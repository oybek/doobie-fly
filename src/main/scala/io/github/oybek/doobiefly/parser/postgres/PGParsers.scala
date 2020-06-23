package io.github.oybek.doobiefly.parser.postgres

import atto._
import Atto._
import cats.implicits._
import atto.Atto.{char, letter, many, many1, whitespace}
import io.github.oybek.doobiefly.parser.{CommonParsers, SQLExpr, SQLParser}
import io.github.oybek.doobiefly.parser.postgres.PGSyntax._

object PGParsers extends SQLParser with PGBasicParsers {

  lazy val identifier = (
    letter ~ many(letter | `_` | digit)
  ).map { case (x, xs) => x + xs.mkString }

  lazy val columnConstraint =
    `null`.map(_ => ColumnNull) |
    `not null`.map(_ => ColumnNotNull) |
    `primary key`.map(_ => ColumnPrimaryKey)

  lazy val column = for {
    (name, ttype) <- identifier ~ (ws1 ~> ttype)
    columnCs <- many(ws1 ~> columnConstraint)
  } yield Column(name, ttype, columnCs)

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

  lazy val tableConstraint =
    (`primary key` ~> ws ~> `(` ~> ws ~> sepBy1(ws ~> identifier <~ ws, `,`) <~ ws <~ `)`).map(PrimaryKey)

  val parser: Parser[PGSQLExpr] =
    for {
      _ <- ws ~> `create`
      _ <- ws1 ~> `table`
      name <- ws1 ~> identifier
      _ <- ws ~> `(`
      columns <- sepBy(ws ~> column <~ ws, `,`)
      constraints <- many(ws ~> `,` ~> ws ~> tableConstraint <~ ws)
      _ <- ws ~> `)`
      _ <- ws
    } yield PGCreateTable(name, columns, constraints)
}
