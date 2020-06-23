package io.github.oybek.doobiefly.parser

import atto.Atto.{char, many, many1, stringCI, whitespace}

trait CommonParsers {

  lazy val `(` = char('(')
  lazy val `)` = char(')')
  lazy val `,` = char(',')
  lazy val `_` = char('_')

  lazy val ws = many(whitespace)
  lazy val ws1 = many1(whitespace)

  def sci(s: String) = stringCI(s)
}
