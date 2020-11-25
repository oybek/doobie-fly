package io.github.oybek.kraken.hub.telegram

import cats.effect.{Async, Concurrent, Sync, Timer}
import cats.implicits._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.github.oybek.kraken.domain.model.Scan
import io.github.oybek.kraken.hub.db.DbAccess
import telegramium.bots.high.implicits._
import telegramium.bots.high.{Api, LongPollBot, Methods}
import telegramium.bots.{ChatIntId, Message}

class TgGate[F[_] : Async : Timer : Concurrent](implicit bot: Api[F], dbAccess: DbAccess[F])
  extends LongPollBot[F](bot)
    with TgExtractors {

  private val log = Slf4jLogger.getLoggerFromName[F]("Core")
  val me: Long = 108683062

  override def onMessage(message: Message): F[Unit] =
    log.info(s"got message: $message") >> (message match {
      case _ if message.chat.id != me =>
        send(message.chat.id, "Пошёл нахуй!")

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
      .void >> log.info(s"send message: $text to $chatId")
}
