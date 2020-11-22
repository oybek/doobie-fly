package io.github.oybek

import doobie.implicits.toSqlInterpolator
import io.github.oybek.kraken.db.migration.model.Migration

package object kraken {

  lazy val migrations = List[Migration](Migration("Create 'scan' table", sql"""
           |create table scan(
           |  id int,
           |  chatId int,
           |  url varchar
           |)
           |""".stripMargin))
}
