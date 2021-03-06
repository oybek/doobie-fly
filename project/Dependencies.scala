import sbt._

object Dependencies {

  object V {
    val catsCore = "2.1.1"
    val catsEffect = "2.1.1"
    val scalaTest = "3.2.0"
    val slf4j = "1.7.26"
    val logback = "1.2.3"
    val doobie = "0.8.8"
    val mock = "4.4.0"
    val mockTest = "3.1.0"
    val jsoup = "1.13.1"
    val pureConfig = "0.13.0"
    val http4s = "0.21.7"
    val telegramium = "2.49.0"
    val fs2 = "2.4.4"
    val log4cats = "1.1.1"
  }

  val catsCore = "org.typelevel" %% "cats-core" % V.catsCore
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
  val scalaTest = "org.scalatest" %% "scalatest" % V.scalaTest % Test
  val pureConfig = "com.github.pureconfig" %% "pureconfig" % V.pureConfig
  val jsoup = "org.jsoup" % "jsoup" % V.jsoup
  val fs2 = "co.fs2" %% "fs2-core" % V.fs2

  val doobie = Seq(
    "org.tpolecat" %% "doobie-core" % V.doobie,
    "org.tpolecat" %% "doobie-postgres" % V.doobie,
    "org.tpolecat" %% "doobie-hikari" % V.doobie,
    "org.tpolecat" %% "doobie-h2" % V.doobie,
    "org.tpolecat" %% "doobie-scalatest" % V.doobie % Test
  )

  val logger = Seq(
    "org.slf4j" % "slf4j-api" % V.slf4j,
    "ch.qos.logback" % "logback-classic" % V.logback,
    "io.chrisdavenport" %% "log4cats-slf4j" % "1.1.1"
  )

  val mock = Seq(
    "org.scalamock" %% "scalamock" % V.mock % Test,
    "org.scalatest" %% "scalatest" % V.mockTest % Test
  )

  val testContainers = Seq(
    "com.dimafeng" %% "testcontainers-scala-core" % "0.37.0" % "test",
    "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.37.0" % "test",
    "com.dimafeng" %% "testcontainers-scala-postgresql" % "0.37.0" % "test"
  )

  val telegramium = Seq(
    "io.github.apimorphism" %% "telegramium-core" % V.telegramium,
    "io.github.apimorphism" %% "telegramium-high" % V.telegramium
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-dsl" % V.http4s,
    "org.http4s" %% "http4s-circe" % V.http4s,
    "org.http4s" %% "http4s-blaze-client" % V.http4s
  )

  val log4cats = Seq(
    "io.chrisdavenport" %% "log4cats-core"    % V.log4cats,
    "io.chrisdavenport" %% "log4cats-slf4j"   % V.log4cats
  )

  val common = Seq(catsCore, catsEffect, scalaTest, jsoup, pureConfig, fs2) ++
    logger ++
    doobie ++
    mock ++
    http4s ++
    telegramium ++
    testContainers ++
    log4cats
}
