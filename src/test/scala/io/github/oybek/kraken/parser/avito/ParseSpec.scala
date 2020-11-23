package io.github.oybek.kraken.parser.avito

import io.github.oybek.kraken.domain.model.Item
import io.github.oybek.kraken.parser.syntax.DocumentOps
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

trait ParseSpec extends AnyFlatSpec with Matchers {

  def itemDocument: Document
  def items: Either[String, List[Item]]

  "Raw html item" must "be parsed" in {
    AvitoItem(itemDocument).as[List[Item]] should be(items)
  }
}
