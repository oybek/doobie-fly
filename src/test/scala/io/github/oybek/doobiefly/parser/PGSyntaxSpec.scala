package io.github.oybek.doobiefly.parser

import atto._
import Atto._
import atto.ParseResult._
import io.github.oybek.doobiefly.parser.postgres.PG
import io.github.oybek.doobiefly.parser.postgres.Syntax.{PG_CreateTable, PG_SQLExpr}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class PGSyntaxSpec extends AnyFlatSpec with Matchers {

  "creating empty table" should "be parsed" in {
    val tests =
      Table(
        ("Raw text", "Parse result"),
        ("CREATE TABLE foo()", Some(PG_CreateTable("foo"))),
        ("CREATE TABLE foo)", None),
        (" CREATE  TABLE  Hello_1        (    ) ", Some(PG_CreateTable("Hello_1"))),
        (" CREATE  TABLE  _Hello_1        (    ) ", None),
      )
    forAll(tests) {
      case (s: String, r: Option[PG_SQLExpr]) =>
        PG.parser.parseOnly(s).option should be (r)
    }
  }
}
