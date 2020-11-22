package io.github.oybek.kraken.hub.avito

import io.github.oybek.kraken.domain.model.Item

trait AvitoAlg[F[_]] {
  def findItems(url: String): F[List[Item]]
}
