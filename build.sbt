name := "doobie-fly"

version := "0.1"

scalaVersion := "2.13.2"

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies ++= Seq(
  "org.tpolecat" %% "atto-core" % "0.8.0",
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)

