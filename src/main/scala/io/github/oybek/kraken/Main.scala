package io.github.oybek.kraken

import java.util.concurrent.TimeUnit

import cats.effect.{Async, Blocker, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import doobie.ExecutionContexts
import doobie.hikari.HikariTransactor
import io.github.oybek.kraken.config.{Config, DbConfig}
import io.github.oybek.kraken.db.migration.migrate
import org.slf4j.LoggerFactory

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
          case (transactor, blocker) =>
            implicit val tx: HikariTransactor[IO] = transactor
            migrate[IO](migrations, Some(x => IO {
              log.info(x)
            }))
        }
    } yield ExitCode.Success

  private def resources(config: Config): Resource[IO, (HikariTransactor[IO], Blocker)] = {
    for {
      connEc <- ExecutionContexts.fixedThreadPool[IO](10)
      tranEc <- ExecutionContexts.cachedThreadPool[IO]
      transactor <- transactor[IO](config.database, connEc, Blocker.liftExecutionContext(tranEc))
      blocker <- Blocker[IO]
    } yield (transactor, blocker)
  }

  def transactor[F[_] : Sync : Async : ContextShift](config: DbConfig,
                                                     ec: ExecutionContext,
                                                     blocker: Blocker): Resource[F, HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor[F](
      config.driver,
      config.url,
      config.user,
      config.password,
      ec,
      blocker
    )
}
