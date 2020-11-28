package io.github.oybek.kraken.domain

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import cats.effect.concurrent.Ref
import cats.effect.{Sync, Timer}
import cats.implicits._
import fs2.Stream
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.github.oybek.kraken.domain.model._
import io.github.oybek.kraken.hub.avito.AvitoAlg
import io.github.oybek.kraken.hub.db.DbAccessAlg
import telegramium.bots.high.implicits._
import telegramium.bots.high.{Api, Methods}
import telegramium.bots.{ChatIntId, InlineKeyboardButton, InlineKeyboardMarkup}

import scala.concurrent.duration._

class Core[F[_] : Sync : Timer](
  implicit avito: AvitoAlg[F],
  bot: Api[F],
  dbAccess: DbAccessAlg[F]
) {
  private val log = Slf4jLogger.getLoggerFromName[F]("Core")

  def start: F[Unit] =
    (Stream.emit(()) ++ Stream.fixedRate[F](2.minutes))
      .evalTap { _ =>
        for {
          _ <- log.info("Waking up...")
          _ <- log.info("Fetching scans...")
          scans <- dbAccess.getScans
          _ <- log.info(s"Fetched $scans")
          res <- scans.traverse_(handleScan).attempt
          _ <- res match {
            case Left(msg) =>
              log.info(msg.getMessage)
            case Right(_) =>
              ().pure[F]
          }
        } yield ()
      }.compile.drain

  val me: Long = 108683062

  def handleScan(scan: Scan): F[Unit] =
    for {
      _ <- log.info(s"Handling $scan...")
      items <- dbAccess.selectItems(scan.id)
      newItems <- avito.findItems(scan.url).value.flatMap {
        case Left(err) =>
          Sync[F].delay(err.printStackTrace()) >>
            send(me, err.getMessage + err.toString).as(List.empty[Item])
        case Right(newItems) =>
          log.info(s"fetched: $newItems, filtering...") >> newItems.filter(item =>
            item.time.toLocalDateTime.isAfter(LocalDateTime.now().minusHours(5))
          ).pure[F]
      }
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
      _ <- dbAccess.upsertItems(scan.id, newItems)
    } yield ()

  implicit class LocalDateTimeOps(timestamp: Timestamp) {
    def readable: String = {
      val diffMinutes = ChronoUnit.MINUTES.between(timestamp.toLocalDateTime, LocalDateTime.now())
      val hours = diffMinutes / 60
      val minutes = diffMinutes % 60
      s"${if (hours > 0) s"$hours часов" else ""} $minutes минут назад"
    }
  }

  def send(chatId: Long, text: String): F[Unit] =
    Methods
      .sendMessage(chatId = ChatIntId(chatId), text = text)
      .exec
      .void >> log.info(s"send message: $text to $chatId")
}
