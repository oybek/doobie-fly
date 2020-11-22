
ThisBuild / version := "0.1"
ThisBuild / organization := "io.github.oybek"

lazy val kraken = (project in file("."))
  .settings(name := "kraken")
  .settings(libraryDependencies ++= Dependencies.common)
  .settings(Compiler.settings)
