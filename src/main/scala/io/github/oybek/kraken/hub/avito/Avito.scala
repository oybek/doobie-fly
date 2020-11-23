package io.github.oybek.kraken.hub.avito

import cats.implicits._
import cats.effect.Sync
import io.github.oybek.kraken.domain.model.Item
import io.github.oybek.kraken.parser.avito.{AvitoPage, itemParser}
import io.github.oybek.kraken.parser.syntax.DocumentOps
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

class Avito[F[_]: Sync] extends AvitoAlg[F] {
  private val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30"

  private val log = LoggerFactory.getLogger("Avito")

  override def findItems(url: String): F[List[Item]] =
    for {
      html <- Sync[F].delay {
        Jsoup
          .connect(url)
          .userAgent(userAgent)
          .get()
      }
      res <- AvitoPage(html).as[List[Item]] match {
        case Left(err) =>
          Sync[F]
            .delay(log.info(err))
            .as(List.empty[Item])
        case Right(res) =>
          res.pure[F]
      }
    } yield res
}
