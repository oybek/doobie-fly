package io.github.oybek

import doobie.implicits.toSqlInterpolator
import io.github.oybek.kraken.migration.model.Migration

package object kraken {

  lazy val migrations = List[Migration](Migration("Create 'scan' table", sql"""
           |create table scan(
           |  id serial,
           |  chatId bigint not null,
           |  url varchar not null
           |)
           |""".stripMargin))
}
