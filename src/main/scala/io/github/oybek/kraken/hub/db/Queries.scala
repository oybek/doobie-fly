package io.github.oybek.kraken.hub.db

import doobie.{Query0, Update0}
import doobie.implicits.toSqlInterpolator
import io.github.oybek.kraken.domain.model.Scan

object Queries {
  val selectScanQ: Query0[Scan] =
    sql"select id, chatid, url from scan".query[Scan]

  def addScanQ(chatId: Long, url: String): Update0 =
    sql"insert into scan(chatid, url) values ($chatId, $url)".update
}
