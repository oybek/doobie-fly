package io.github.oybek.doobiefly.parser

import atto.Parser

trait SQLParserProvider {
  def parser: Parser[SQLExpr]
}
