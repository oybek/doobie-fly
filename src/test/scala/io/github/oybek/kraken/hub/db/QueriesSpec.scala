package io.github.oybek.kraken.hub.db

import cats.effect.IO
import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import doobie.scalatest.IOChecker
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
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
    check(Queries.addScanQ(1, "hello"))
  }
}
