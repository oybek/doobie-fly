package io.github.oybek.doobiefly.parser

import atto.Atto._
import atto._
import cats.data.NonEmptyList
import io.github.oybek.doobiefly.parser.postgres.PGParsers
import io.github.oybek.doobiefly.parser.postgres.PGSyntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class PGSyntaxSpec extends AnyFlatSpec with Matchers {

  "column" should "be parsed" in {
    val column = "author varchar not null"
    val result = PGParsers.column.parseOnly(column)
    result.either should be(Right(Column("author", PGVarchar(None), List(ColumnNotNull))))
  }

  "creating table" should "be parsed" in {
    val tests =
      Table(
        ("Raw text", "Parse result"),
        ("CREATE TABLE foo()", Right(PGCreateTable("foo"))),
        ("CREATE TABLE foo)", Left("")),
        (
          " CREATE  TABLE  Hello_1        (    ) ",
          Right(PGCreateTable("Hello_1"))
        ),
        (" CREATE  TABLE  _Hello_1        (    ) ", Left("")),
        (
          """
            |CREATE TABLE Student(
            |  name character varying (40),
            |  age integer,
            |  cash real
            |)
            |""".stripMargin,
          Right(
            PGCreateTable(
              name = "Student",
              columns = List(
                Column("name", PGVarchar(Some(40)), List()),
                Column("age", PGInteger, List()),
                Column("cash", PGReal, List()),
              )
            )
          )
        ),
        ("CREATE TABLE Student(name string, age integer)", Left("")),
        (
          "create table foobar(i bigint)",
          Right(PGCreateTable("foobar", List(Column("i", PGBigInt, List()))))
        ),
        (
          "create table Book(author varchar not null)",
          Right(
            PGCreateTable(
              name = "Book",
              columns =
                List(Column("author", PGVarchar(None), List(ColumnNotNull)))
            )
          )
        ),
        (
          """
            |create table User (
            | age integer not null,
            | name varchar not null,
            | primary key (name, age)
            |)",
            |""".stripMargin,
          Right(
            PGCreateTable(
              name = "User",
              columns = List(
                Column("age", PGInteger, List(ColumnNotNull)),
                Column("name", PGVarchar(None), List(ColumnNotNull))
              ),
              constraints = List(PrimaryKey(NonEmptyList.of("name", "age")))
            )
          )
        ),
        (
          """
            |create table User (
            | id integer primary key,
            | age integer not null,
            | name varchar not null
            |)",
            |""".stripMargin,
          Right(
            PGCreateTable(
              name = "User",
              columns = List(
                Column("id", PGInteger, List(ColumnPrimaryKey)),
                Column("age", PGInteger, List(ColumnNotNull)),
                Column("name", PGVarchar(None), List(ColumnNotNull))
              )
            )
          )
        ),
      )
    forAll(tests) {
      case (s: String, Right(r)) =>
        PGParsers.parser.parseOnly(s).either should be(Right(r))
      case (s: String, Left(_)) =>
        PGParsers.parser.parseOnly(s).either.isLeft should be(true)
    }
  }
}
