package io.github.oybek.kraken.hub.db

import io.github.oybek.kraken.domain.model.{Item, Scan}

trait DbAccessAlg[F[_]] {
  def getScans: F[List[Scan]]
  def addScan(scan: Scan): F[Int]

  def selectItems(scanId: Int): F[List[Item]]
  def upsertItems(scanId: Int, items: List[Item]): F[Unit]
}
