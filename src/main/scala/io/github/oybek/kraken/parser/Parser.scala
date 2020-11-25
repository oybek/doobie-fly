package io.github.oybek.kraken.parser

import io.github.oybek.kraken.parser.avito.AvitoElement

trait Parser[T] {
  def parse(document: AvitoElement): Either[Throwable, T]
}
