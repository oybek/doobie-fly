package io.github.oybek.kraken.hub.db

import io.github.oybek.kraken.domain.model.Scan

trait DbAccessAlg[F[_]] {
  def getScans: F[List[Scan]]
  def addScan(scan: Scan): F[Boolean]
}
