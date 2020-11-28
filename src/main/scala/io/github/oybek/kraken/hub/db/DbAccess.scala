package io.github.oybek.kraken.hub.db

import cats.data.NonEmptyList
import cats.implicits._
import cats.effect.Bracket
import cats.instances.list._
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.util.update.Update
import io.github.oybek.kraken.domain.model.{Item, Scan}

class DbAccess[F[_] : Bracket[*[_], Throwable]](implicit tx: Transactor[F]) extends DbAccessAlg[F] {

  override def getScans: F[List[Scan]] =
    Queries
      .selectScanQ
      .to[List]
      .transact(tx)

  override def addScan(scan: Scan): F[Int] =
    Queries.upsertScanQ(scan.chatId, scan.url)
      .withUniqueGeneratedKeys[Int]("id")
      .transact(tx)

  override def selectItems(scanId: Int): F[List[Item]] =
    Queries
      .selectItemQ(scanId)
      .to[List]
      .transact(tx)

  override def upsertItems(scanId: Int, items: List[Item]): F[Unit] =
    items.map(item =>
      Queries.upsertItemQ(scanId, item).run
    ).reduce(_ >> _).transact(tx).void
}
