package io.github.oybek.doobiefly.parser.postgres

import atto._
import Atto._
import cats.implicits._
import atto.Atto.{char, letter, many, many1, stringCI, whitespace}
import io.github.oybek.doobiefly.parser.{SQLExpr, SQLParserProvider}
import io.github.oybek.doobiefly.parser.postgres.Syntax.{PG_CreateTable, PG_SQLExpr}

object PG extends SQLParserProvider {

  private lazy val identifier = (
    letter ~ many(letter | char('_') | digit)
  ).map { case (x, xs) => x + xs.mkString }

  val parser: Parser[PG_SQLExpr] =
    for {
      _ <- many(whitespace) ~> stringCI("create")
      _ <- many1(whitespace) ~> stringCI("table")
      name <- many1(whitespace) ~> identifier
      _ <- many(whitespace) ~> char('(')
      _ <- many(whitespace)
      _ <- char(')')
      _ <- many(whitespace)
    } yield PG_CreateTable(name)
}
