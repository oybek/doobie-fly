package io.github.oybek.kraken.parser

import org.jsoup.nodes.Document

trait Parser[T] {
  def parse(document: Document): Either[String, T]
}
