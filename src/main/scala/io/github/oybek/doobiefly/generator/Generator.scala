package io.github.oybek.doobiefly.generator

import io.github.oybek.doobiefly.parser.SQLExpr

trait Generator[T <: SQLExpr] {

  def gen(expr: T): Option[Code]
}
