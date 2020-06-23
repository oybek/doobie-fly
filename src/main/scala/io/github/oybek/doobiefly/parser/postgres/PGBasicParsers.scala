package io.github.oybek.doobiefly.parser.postgres

import atto._
import Atto._
import io.github.oybek.doobiefly.parser.CommonParsers
import io.github.oybek.doobiefly.parser.postgres.PGParsers.sci

trait PGBasicParsers extends CommonParsers {

  lazy val `primary key` = sci("primary") ~> ws1 ~> sci("key")
  lazy val `not null` = sci("not") ~> ws1 ~> sci("null")
  lazy val `null` = sci("null")
  lazy val `create` = sci("create")
  lazy val `table` = sci("table")
}
