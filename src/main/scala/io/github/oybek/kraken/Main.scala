package io.github.oybek.kraken

import java.util.concurrent.TimeUnit

import cats.effect.{
  Async,
  Blocker,
  ContextShift,
  ExitCode,
  IO,
  IOApp,
  Resource,
  Sync
}
import doobie.ExecutionContexts
import doobie.hikari.HikariTransactor
import io.github.oybek.kraken.config.{Config, DbConfig}
import io.github.oybek.kraken.migration.migrate
import io.github.oybek.kraken.hub.TgGate
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.middleware.Logger
import org.slf4j.LoggerFactory
import telegramium.bots.high.{Api, BotApi, LongPollBot}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

object Main extends IOApp {
  private val log = LoggerFactory.getLogger("Main")

  override def run(args: List[String]): IO[ExitCode] =
    for {
      configFile <- IO {
        Option(System.getProperty("application.conf"))
      }
      config <- Config.load[IO](configFile)
      _ <- IO {
        log.info(s"loaded config: $config")
      }
      _ <- resources(config)
        .use {
          case (transactor, httpClient, blocker) =>
            implicit val tx: HikariTransactor[IO] = transactor
            implicit val client: Client[IO] =
              Logger(logHeaders = false, logBody = false)(httpClient)
            implicit val tgBotApi: Api[IO] = new BotApi[IO](
              client,
              s"https://api.telegram.org/bot${config.tgBotApiToken}",
              blocker
            )
            implicit val tgGate: LongPollBot[IO] = new TgGate[IO]()
            for {
              _ <- migrate[IO](migrations, Some(x => IO { log.info(x) }))
              _ <- tgGate.start
            } yield ()
        }
    } yield ExitCode.Success

  private def resources(
    config: Config
  ): Resource[IO, (HikariTransactor[IO], Client[IO], Blocker)] = {
    for {
      httpCp <- ExecutionContexts.cachedThreadPool[IO]
      connEc <- ExecutionContexts.fixedThreadPool[IO](10)
      tranEc <- ExecutionContexts.cachedThreadPool[IO]
      httpCl <- BlazeClientBuilder[IO](httpCp)
        .withResponseHeaderTimeout(FiniteDuration(60, TimeUnit.SECONDS))
        .resource
      transactor <- transactor[IO](
        config.database,
        connEc,
        Blocker.liftExecutionContext(tranEc)
      )
      blocker <- Blocker[IO]
    } yield (transactor, httpCl, blocker)
  }

  def transactor[F[_]: Sync: Async: ContextShift](
    config: DbConfig,
    ec: ExecutionContext,
    blocker: Blocker
  ): Resource[F, HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor[F](
      config.driver,
      config.url,
      config.user,
      config.password,
      ec,
      blocker
    )
}
