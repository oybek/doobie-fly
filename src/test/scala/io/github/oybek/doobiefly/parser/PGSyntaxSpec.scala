package io.github.oybek.doobiefly.parser

import atto.Atto._
import atto._
import cats.data.NonEmptyList
import io.github.oybek.doobiefly.parser.postgres.PG
import io.github.oybek.doobiefly.parser.postgres.Syntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class PGSyntaxSpec extends AnyFlatSpec with Matchers {

  "column" should "be parsed" in {
    val column = "author varchar not null"
    PG.column
      .parse(column)
      .option should be(Some(Column("author", PGVarchar(None), false)))
  }

  "creating table" should "be parsed" in {
    val tests =
      Table(
        ("Raw text", "Parse result"),
        ("CREATE TABLE foo()", Right(PG_CreateTable("foo"))),
        ("CREATE TABLE foo)", Left("")),
        (
          " CREATE  TABLE  Hello_1        (    ) ",
          Right(PG_CreateTable("Hello_1"))
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
            PG_CreateTable(
              name = "Student",
              columns = List(
                Column("name", PGVarchar(Some(40))),
                Column("age", PGInteger),
                Column("cash", PGReal),
              )
            )
          )
        ),
        ("CREATE TABLE Student(name string, age integer)", Left("")),
        (
          "create table foobar(i bigint)",
          Right(PG_CreateTable("foobar", List(Column("i", PGBigInt))))
        ),
        (
          "create table Book(author varchar not null)",
          Right(
            PG_CreateTable(
              name = "Book",
              columns =
                List(Column("author", PGVarchar(None), nullable = false))
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
            PG_CreateTable(
              name = "User",
              columns = List(
                Column("age", PGInteger, nullable = false),
                Column("name", PGVarchar(None), nullable = false)
              ),
              constraints = List(PrimaryKey(NonEmptyList.of("name", "age")))
            )
          )
        ),
      )
    forAll(tests) {
      case (s: String, Right(r)) =>
        PG.parser.parseOnly(s).either should be(Right(r))
      case (s: String, Left(_)) =>
        PG.parser.parseOnly(s).either.isLeft should be(true)
    }
  }
}
