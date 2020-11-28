package io.github.oybek.kraken.hub.db

import java.sql.Timestamp
import java.time.LocalDateTime

import cats.effect.IO
import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import doobie.scalatest.IOChecker
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import io.github.oybek.kraken.domain.model.Item
import io.github.oybek.kraken.migration.migrate
import io.github.oybek.kraken.migrations
import org.scalatest.funsuite.AnyFunSuite

class QueriesSpec extends AnyFunSuite with IOChecker with ForAllTestContainer {
  override val container = PostgreSQLContainer()
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  implicit lazy val transactor = {
    Transactor
      .fromDriverManager[IO](
        container.driverClassName,
        container.jdbcUrl,
        container.username,
        container.password
      )
  }

  override def afterStart(): Unit =
    migrate[IO](migrations).unsafeRunSync()

  test("select scan") {
    check(Queries.selectScanQ)
  }

  test("insert scan") {
    check(Queries.upsertScanQ(1, "hello"))
  }

  test("upsert item") {
    check(Queries.upsertItemQ(1, Item("", "", Timestamp.valueOf(LocalDateTime.now()), 0)))
  }

  test("select item") {
    check(Queries.selectItemQ(1))
  }
}
