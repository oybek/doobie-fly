package io.github.oybek.kraken.hub

import cats.implicits._
import cats.effect.{Async, Concurrent, Sync, Timer}
import org.slf4j.{Logger, LoggerFactory}
import telegramium.bots.{ChatIntId, Message}
import telegramium.bots.high.{Api, LongPollBot, Methods}
import telegramium.bots.high.implicits._

class TgGate[F[_]: Async: Timer: Concurrent]()(implicit bot: Api[F])
    extends LongPollBot[F](bot)
    with TgExtractors {

  val log: Logger = LoggerFactory.getLogger("TgGate")

  override def onMessage(message: Message): F[Unit] =
    Sync[F].delay { log.info(s"got message: $message") } >> (message match {
      case Text(text) => send(message.chat.id, text)
      case _          => Sync[F].unit
    })

  def send(chatId: Long, text: String): F[Unit] =
    Methods
      .sendMessage(chatId = ChatIntId(chatId), text = text)
      .exec
      .void >>
      Sync[F].delay { log.info(s"send message: $text to $chatId") }
}
