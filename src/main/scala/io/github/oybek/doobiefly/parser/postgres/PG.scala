package io.github.oybek.doobiefly.parser.postgres

import atto._
import Atto._
import cats.implicits._
import atto.Atto.{char, letter, many, many1, stringCI, whitespace}
import io.github.oybek.doobiefly.parser.{SQLExpr, SQLParserProvider}
import io.github.oybek.doobiefly.parser.postgres.Syntax.{PG_CreateTable, PG_SQLExpr}

object PG extends SQLParserProvider {

  val parser: Parser[PG_SQLExpr] =
    for {
      _ <- many(whitespace)
      _ <- stringCI("create")
      _ <- many1(whitespace)
      _ <- stringCI("table")
      _ <- many1(whitespace)
      (x, xs) <- letter ~ many(letter | char('_') | digit)
      _ <- many(whitespace)
      _ <- char('(')
      _ <- many(whitespace)
      _ <- char(')')
      _ <- many(whitespace)
    } yield PG_CreateTable(x + xs.mkString)
}
