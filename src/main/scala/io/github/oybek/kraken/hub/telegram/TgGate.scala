package io.github.oybek.kraken.hub.telegram

import cats.effect.{Async, Concurrent, Sync, Timer}
import cats.implicits._
import io.github.oybek.kraken.domain.model.Scan
import io.github.oybek.kraken.hub.db.DbAccess
import org.slf4j.{Logger, LoggerFactory}
import telegramium.bots.high.implicits._
import telegramium.bots.high.{Api, LongPollBot, Methods}
import telegramium.bots.{ChatIntId, Message}

class TgGate[F[_] : Async : Timer : Concurrent](implicit bot: Api[F], dbAccess: DbAccess[F])
  extends LongPollBot[F](bot)
    with TgExtractors {

  val log: Logger = LoggerFactory.getLogger("TgGate")

  override def onMessage(message: Message): F[Unit] =
    Sync[F].delay {
      log.info(s"got message: $message")
    } >> (message match {
      case Text(text) if text.startsWith("https://") =>
        for {
          res <- dbAccess.addScan(Scan(chatId = message.chat.id, url = text.trim))
          _ <- send(message.chat.id, res.toString)
        } yield ()
      case _ =>
        send(message.chat.id,
          """
            |Send me a link
            |""".stripMargin)
    })

  def send(chatId: Long, text: String): F[Unit] =
    Methods
      .sendMessage(chatId = ChatIntId(chatId), text = text)
      .exec
      .void >>
      Sync[F].delay {
        log.info(s"send message: $text to $chatId")
      }
}
