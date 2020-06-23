package io.github.oybek.doobiefly.parser.postgres

import cats.data.NonEmptyList
import io.github.oybek.doobiefly.parser.SQLExpr

object PGSyntax {

  sealed trait PGSQLExpr extends SQLExpr
  case class PGCreateTable(name: String,
                           columns: List[Column] = List.empty[Column],
                           constraints: List[Constraint] =
                              List.empty[Constraint])
      extends PGSQLExpr

  final case class Column(name: String, ttype: PGType, constraints: List[ColumnConstraints])

  sealed trait Constraint
  case class PrimaryKey(columnNames: NonEmptyList[String]) extends Constraint

  sealed trait ColumnConstraints
  case object ColumnNull extends ColumnConstraints
  case object ColumnNotNull extends ColumnConstraints
  case object ColumnPrimaryKey extends ColumnConstraints

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
