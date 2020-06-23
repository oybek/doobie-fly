package io.github.oybek.doobiefly.generator

import atto.Atto._
import cats.data.NonEmptyList
import io.github.oybek.doobiefly.generator.postgres.PGGenerator
import io.github.oybek.doobiefly.parser.postgres.PGParsers
import io.github.oybek.doobiefly.parser.postgres.PGSyntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._

class PGGeneratorSpec extends AnyFlatSpec with Matchers {

  "I" should "check generation" in {
    val sql =
      """
        |create table Student(
        |  id integer not null,
        |  name varchar,
        |  age integer
        |)
        |""".stripMargin
    PGParsers
      .parser
      .parseOnly(sql)
      .option
      .flatMap(x => PGGenerator.gen(x))
      .map { x =>
        println(x.content)
        x
      }
      .nonEmpty should be (true)
  }
}
