
name := "kraken"

version := "0.1"

scalaVersion := "2.13.2"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.13.1",
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)
