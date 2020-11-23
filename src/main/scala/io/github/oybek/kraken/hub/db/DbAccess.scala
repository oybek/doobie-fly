package io.github.oybek.kraken.hub.db

import cats.effect.Bracket
import doobie.implicits._
import doobie.util.transactor.Transactor
import io.github.oybek.kraken.domain.model.Scan

class DbAccess[F[_] : Bracket[*[_], Throwable]](implicit tx: Transactor[F]) extends DbAccessAlg[F] {

  override def getScans: F[List[Scan]] =
    Queries
      .selectScanQ
      .to[List]
      .transact(tx)

  override def addScan(scan: Scan): F[Int] =
    Queries.addScanQ(scan.chatId, scan.url)
      .run
      .transact(tx)
}
