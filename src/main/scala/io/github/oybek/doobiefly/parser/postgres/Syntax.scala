package io.github.oybek.doobiefly.parser.postgres

import io.github.oybek.doobiefly.parser.SQLExpr

object Syntax {

  sealed trait PG_SQLExpr extends SQLExpr
  case class PG_CreateTable(name: String,
                            columns: List[Column] = List.empty[Column])
      extends PG_SQLExpr

  final case class Column(name: String, ttype: PGType, nullable: Boolean = true)

  sealed trait PGType
  case class PGBit(length: Option[Int]) extends PGType
  case class PGVarbit(length: Option[Int]) extends PGType
  case class PGVarchar(length: Option[Int]) extends PGType
  case object PGBigInt extends PGType
  case object PGInteger extends PGType
  case object PGReal extends PGType
  case object PGDecimal extends PGType
  case object PGBigSerial extends PGType
  case object PGBool extends PGType
}
