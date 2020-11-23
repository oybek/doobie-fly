package io.github.oybek.kraken.domain

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import fs2.Stream
import cats.implicits._
import cats.effect.{Sync, Timer}
import cats.effect.concurrent.Ref
import io.github.oybek.kraken.domain.model._
import io.github.oybek.kraken.hub.avito.AvitoAlg
import io.github.oybek.kraken.hub.db.DbAccessAlg
import org.slf4j.LoggerFactory
import telegramium.bots.{ChatIntId, InlineKeyboardButton, InlineKeyboardMarkup}
import telegramium.bots.high.{Api, Methods}
import telegramium.bots.high.implicits._

import scala.concurrent.duration._

class Core[F[_] : Sync : Timer](cacheRef: Ref[F, Map[String, List[Item]]])(
  implicit avito: AvitoAlg[F],
  bot: Api[F],
  dbAccess: DbAccessAlg[F]
) {

  private val log = LoggerFactory.getLogger("Core")

  def start: F[Unit] =
    (Stream.emit(()) ++ Stream.fixedRate[F](2.minutes))
      .evalTap { _ =>
        for {
          _ <- Sync[F].delay(log.info("Waking up..."))
          _ <- Sync[F].delay(log.info("Fetching scans..."))
          scans <- dbAccess.getScans
          _ <- Sync[F].delay(log.info(s"Fetched $scans"))
          res <- scans.traverse_(handleScan).attempt
          _ <- res match {
            case Left(msg) =>
              Sync[F].delay(log.info(msg.getMessage))
            case Right(_) =>
              ().pure[F]
          }
        } yield ()
      }.compile.drain

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
            .sendMessage(chatId = ChatIntId(scan.chatId), text =
              s"""
                 |Новое объявление
                 |Название: ${item.name}
                 |Цена: ${item.cost}
                 |Дата: ${item.time.readable}
                 |""".stripMargin,
              replyMarkup = InlineKeyboardMarkup(
                List(List(InlineKeyboardButton(text = "Посмотреть", url = item.link.some)))
              ).some
            )
            .exec
            .void
        case ItemChanged(prev, item) =>
          Methods
            .sendMessage(chatId = ChatIntId(scan.chatId), text =
              s"""
                 |Объявление обновилось
                 |Название: ${prev.name} -> ${item.name}
                 |Цена: ${prev.cost} -> ${item.cost}
                 |Дата: ${item.time.readable}
                 |""".stripMargin,
              replyMarkup = InlineKeyboardMarkup(
                List(List(InlineKeyboardButton(text = "Посмотреть", url = item.link.some)))
              ).some
            )
            .exec
            .void
        case ItemDeleted(_) => ().pure[F]
      }
      _ <- cacheRef.update(cache => cache.updated(scan.url, (items ++ newItems).distinctBy(_.link)))
    } yield ()

  implicit class LocalDateTimeOps(localDateTime: LocalDateTime) {
    def readable: String = {
      val diffMinutes = ChronoUnit.MINUTES.between(localDateTime, LocalDateTime.now())
      val hours = diffMinutes / 60
      val minutes = diffMinutes % 60
      s"${if (hours > 0) s"$hours часов" else ""} $minutes минут назад"
    }
  }
}
