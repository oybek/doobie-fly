package io.github.oybek.doobiefly.parser

import atto.Parser

trait SQLParser {
  def parser: Parser[SQLExpr]
}
