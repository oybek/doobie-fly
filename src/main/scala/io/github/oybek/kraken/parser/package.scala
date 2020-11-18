package io.github.oybek.kraken

import org.jsoup.nodes.Document

package object parser {

  object syntax {
    implicit class DocumentOps(val document: Document) extends AnyVal {
      def as[T](implicit parser: Parser[T]): Either[String, T] =
        parser.parse(document)
    }
  }
}
