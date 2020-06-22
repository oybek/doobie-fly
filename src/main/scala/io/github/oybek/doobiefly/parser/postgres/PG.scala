package io.github.oybek.doobiefly.parser.postgres

import atto._
import Atto._
import cats.implicits._
import atto.Atto.{char, letter, many, many1, whitespace}
import io.github.oybek.doobiefly.parser.{SQLExpr, SQLParserProvider}
import io.github.oybek.doobiefly.parser.postgres.Syntax._

object PG extends SQLParserProvider {

  lazy val `(` = char('(')
  lazy val `)` = char(')')
  lazy val ws = many(whitespace)
  lazy val ws1 = many1(whitespace)
  def sci(s: String) = stringCI(s)

  lazy val identifier = (
    letter ~ many(letter | char('_') | digit)
  ).map { case (x, xs) => x + xs.mkString }

  lazy val column = for {
    name <- identifier
    ttype <- ws1 ~> ttype
    nullable <- opt(ws1 ~> sci("not") ~> ws1 ~> sci("null")).map(_.isEmpty)
  } yield Column(name, ttype, nullable)

  def varType[T](ps: Parser[String]) =
    ps ~> opt(
      ws ~> (`(` ~> ws ~> many(digit) <~ ws <~ `)`)
        .map(_.mkString.toInt)
    )

  lazy val ttype = {
    (sci("bigint") | sci("int8")).map(_ => PGBigInt) |
      (sci("bigserial") | sci("serial")).map(_ => PGBigSerial) |
      (sci("boolean") | sci("bool")).map(_ => PGBool) |
      (sci("integer") | sci("int") | sci("int4")).map(_ => PGInteger) |
      (sci("real") | sci("float4")).map(_ => PGReal) |
      (sci("numeric") | sci("decimal")).map(_ => PGDecimal) |
      varType(sci("bit")).map(PGBit) |
      varType(sci("varchar") | sci("character") ~> ws1 ~> sci("varying"))
        .map(PGVarchar) |
      varType(sci("varbit") | sci("bit") ~> ws1 ~> sci("varying")).map(PGVarbit)
  }

  lazy val tableContraint = {
    (sci("primary") ~> ws1 ~> sci("key") ~> ws ~> `(` ~> ws ~> sepBy1(
      ws ~> identifier <~ ws,
      char(',')
    ) <~ ws <~ `)`).map(PrimaryKey)
  }

  val parser: Parser[PG_SQLExpr] =
    for {
      _ <- ws ~> sci("create")
      _ <- ws1 ~> sci("table")
      name <- ws1 ~> identifier
      _ <- ws ~> `(`
      columns <- sepBy(ws ~> column <~ ws, char(','))
      constraints <- many(ws ~> char(',') ~> ws ~> tableContraint <~ ws)
      _ <- ws ~> `)`
      _ <- ws
    } yield PG_CreateTable(name, columns, constraints)
}
