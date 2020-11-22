package io.github.oybek.kraken.domain

import cats.implicits._
import cats.effect.{Sync, Timer}
import cats.effect.concurrent.Ref
import io.github.oybek.kraken.domain.model._
import io.github.oybek.kraken.hub.avito.AvitoAlg
import io.github.oybek.kraken.hub.db.DbAccessAlg
import org.slf4j.LoggerFactory
import telegramium.bots.ChatIntId
import telegramium.bots.high.{Api, Methods}
import telegramium.bots.high.implicits._

import scala.concurrent.duration._

class Core[F[_]: Sync: Timer](cacheRef: Ref[F, Map[String, List[Item]]])(
  implicit avito: AvitoAlg[F],
  bot: Api[F],
  dbAccess: DbAccessAlg[F]
) {

  private val log = LoggerFactory.getLogger("Logic")

  def start: F[Unit] =
    for {
      _ <- Sync[F].delay(log.info("Waking up..."))
      _ <- Sync[F].delay(log.info("Fetching scans..."))
      scans <- dbAccess.getScans
      _ <- Sync[F].delay(log.info(s"Fetched $scans"))
      _ <- scans.traverse_(handleScan)
      _ <- Timer[F].sleep(2 minutes)
      _ <- start
    } yield ()

  def handleScan(scan: Scan): F[Unit] =
    for {
      _ <- Sync[F].delay(log.info(s"Handling $scan..."))
      cache <- cacheRef.get
      items = cache.getOrElse(scan.url, List.empty[Item])
      newItems <- avito.findItems(scan.url)
      events = diff(items, newItems)
      _ <- events.traverse_ {
        case ItemCreated(item) =>
          Methods
            .sendMessage(chatId = ChatIntId(scan.chatId), text = s"""
                |Новое объявление
                |${item.link}
                |""".stripMargin)
            .exec
            .void
        case ItemChanged(_, item) =>
          Methods
            .sendMessage(chatId = ChatIntId(scan.chatId), text = s"""
                |Объявление обновилось
                |${item.link}
                |""".stripMargin)
            .exec
            .void
        case ItemDeleted(_) => ().pure[F]
      }
      _ <- cacheRef.update(cache => cache.updated(scan.url, newItems))
    } yield ()
}
