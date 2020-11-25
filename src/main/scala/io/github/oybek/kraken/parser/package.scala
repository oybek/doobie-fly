package io.github.oybek.kraken

import io.github.oybek.kraken.parser.avito.AvitoElement

package object parser {

  object syntax {
    implicit class DocumentOps(val document: AvitoElement) extends AnyVal {
      def as[T](implicit parser: Parser[T]): Either[Throwable, T] =
        parser.parse(document)
    }
  }
}
