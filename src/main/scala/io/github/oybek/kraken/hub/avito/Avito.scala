package io.github.oybek.kraken.hub.avito

import cats.data.EitherT
import cats.implicits._
import cats.effect.Sync
import io.github.oybek.kraken.domain.model.Item
import io.github.oybek.kraken.parser.avito.{AvitoPage, itemParser}
import io.github.oybek.kraken.parser.syntax.DocumentOps
import org.jsoup.Jsoup

class Avito[F[_] : Sync] extends AvitoAlg[F] {
  private val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30"

  override def findItems(url: String): EitherT[F, Throwable, List[Item]] =
    for {
      html <- EitherT(Sync[F].delay {
        Jsoup
          .connect(url)
          .userAgent(userAgent)
          .get()
      }.attempt)
      items <- EitherT(AvitoPage(html).as[List[Item]].pure[F])
    } yield items
}
