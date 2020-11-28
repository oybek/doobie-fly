package io.github.oybek

import doobie.implicits.toSqlInterpolator
import io.github.oybek.kraken.migration.model.Migration

package object kraken {

  lazy val migrations = List[Migration](
    Migration("Create 'scan' table",
      sql"""
           |create table scan(
           |  id serial primary key,
           |  chat_id bigint not null,
           |  url varchar unique not null
           |)
           |""".stripMargin),
    Migration("Create 'item' table",
      sql"""
           |create table item(
           |  id serial primary key,
           |  scan_id integer references scan (id),
           |  url varchar unique not null,
           |  name varchar not null,
           |  time timestamp not null,
           |  cost integer not null
           |)
           |""".stripMargin),
  )
}
