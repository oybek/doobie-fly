package io.github.oybek.kraken.db.migration.model

import java.math.BigInteger

import cats.data.NonEmptyList
import cats.implicits._
import doobie.Fragment
import io.github.oybek.kraken.db.migration.showFragment

final case class Migration(label: String, fr: Fragment, frs: Fragment*) {
  lazy val md5Hash: String = {
    val md5Hash = java.security.MessageDigest
      .getInstance("MD5")
      .digest(NonEmptyList.of(fr, frs: _*).mkString_(";").getBytes)
    val bigInt = new BigInteger(1, md5Hash)
    bigInt.toString(16).toUpperCase
  }
}
