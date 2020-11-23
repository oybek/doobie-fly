package io.github.oybek.kraken.parser.avito

import io.github.oybek.kraken.domain.model.Item
import io.github.oybek.kraken.parser.syntax.DocumentOps
import org.jsoup.Jsoup
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FullPageParseSpec extends AnyFlatSpec with Matchers {

  "Rx550SearchPage parse" must "work" in {
    val content = scala.io.Source.fromFile(s"src/test/resources/Rx550SearchPage.html").getLines().mkString
    val res = AvitoPage(Jsoup.parse(content)).as[List[Item]]
    println(res)
    res.map(_.length) should be (Right(24))
  }
}
