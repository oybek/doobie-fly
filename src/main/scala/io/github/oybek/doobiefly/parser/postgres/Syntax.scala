package io.github.oybek.doobiefly.parser.postgres

import io.github.oybek.doobiefly.parser.SQLExpr

object Syntax {

  sealed trait PG_SQLExpr extends SQLExpr
  case class PG_CreateTable(name: String) extends PG_SQLExpr
}
