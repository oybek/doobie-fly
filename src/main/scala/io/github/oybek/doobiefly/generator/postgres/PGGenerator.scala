package io.github.oybek.doobiefly.generator.postgres

import io.github.oybek.doobiefly.generator.{Code, Generator}
import io.github.oybek.doobiefly.parser.SQLExpr
import io.github.oybek.doobiefly.parser.postgres.PGSyntax
import io.github.oybek.doobiefly.parser.postgres.PGSyntax.{ColumnNull, PGCreateTable, PGSQLExpr}

object PGGenerator extends Generator[PGSQLExpr] {

  override def gen(expr: PGSQLExpr): Option[Code] = expr match {
    case x: PGCreateTable =>
      Some(
        Code(
          name = "",
          content = genInsert(x)
        )
      )
  }

  private val genInsert: PGCreateTable => String = {
    case PGCreateTable(name, columns, constraints) =>
      s"""
         |def insert(x: ${name.capitalize}): Update0 =
         |  Seq(
         |    fr"insert into $name(",
         |    Seq(${genNames(columns)}).reduce(_ ++ fr"," ++ _),
         |    fr")values(",
         |    Seq(${genValues(columns)}).reduce(_ ++ fr"," ++ _),
         |    fr")"
         |  ).reduce(_ ++ _).update
         |""".stripMargin
  }

  private def genNames(columns: List[PGSyntax.Column]) =
    columns.map(x => s""" fr"${x.name}" """).mkString(",")

  private def genValues(columns: List[PGSyntax.Column]) =
    columns.map {
      case PGSyntax.Column(name, _, Nil | List(ColumnNull)) =>
        s""" $name.fold(fr"default")(_ => fr"$${x.$name}") """
      case PGSyntax.Column(name, _, _) =>
        s""" fr"$${x.$name}" """
    }
}
