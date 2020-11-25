package io.github.oybek.kraken.hub.avito

import cats.data.EitherT
import io.github.oybek.kraken.domain.model.Item

trait AvitoAlg[F[_]] {
  def findItems(url: String): EitherT[F, Throwable, List[Item]]
}
