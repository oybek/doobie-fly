package io.github.oybek.kraken.hub.db

import doobie.{Query0, Update0}
import doobie.implicits.toSqlInterpolator
import doobie.implicits.javasql._
import io.github.oybek.kraken.domain.model.{Item, Scan}

object Queries {
  val selectScanQ: Query0[Scan] =
    sql"select id, chat_id, url from scan"
      .query[Scan]

  def upsertScanQ(chatId: Long, url: String): Update0 =
    sql"""
         |insert into scan(chat_id, url) values($chatId, $url)
         |on conflict(url) do update set chat_id = $chatId
         |""".stripMargin.update

  def selectItemQ(scanId: Int): Query0[Item] =
    sql"select url, name, time, cost from item where scan_id = $scanId".query[Item]

  def upsertItemQ(scanId: Int, item: Item): Update0 =
    sql"""
         |insert into item(scan_id, url, name, time, cost) values($scanId, ${item.link}, ${item.name}, ${item.time}, ${item.cost})
         |on conflict(url) do update set name = ${item.name}, cost = ${item.cost}
         |""".stripMargin.update
}
