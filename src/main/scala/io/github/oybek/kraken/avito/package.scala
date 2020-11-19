package io.github.oybek.kraken

import java.time.LocalDateTime

import cats.implicits.catsSyntaxEitherId
import io.github.oybek.kraken.parser.Parser
import org.jsoup.nodes.{Document, Element}

package object avito {

  implicit val itemParser: Parser[Item] = (document: Document) =>
    for {
      aWithLink <- document.findFirst("a[data-marker$=item-title]")
      titleSpan <- aWithLink.findFirst("span")
      divWithDate <- document.findFirst("div[data-marker$=item-date]")
      dateSource = List(divWithDate.attr("data-tooltip"), divWithDate.text())
      dateRegex = (s"""(\\d+) (${monthsToNum.keys.mkString("|")}) (\\d+):(\\d+)""").r
      time <- dateSource.collectFirst {
        case dateRegex(dayOfMonth, month, hh, mm) =>
          LocalDateTime
            .now()
            .withDayOfMonth(dayOfMonth.toInt)
            .withMonth(monthsToNum.getOrElse(month, 1))
            .withHour(hh.toInt)
            .withMinute(mm.toInt)
            .withSecond(0)
            .withNano(0)
      }.fold(s"Can't parse date from $dateSource".asLeft[LocalDateTime])(_.asRight[String])
      spanWithPrice <- document.findFirst("span[data-marker$=item-price]")
      price <- spanWithPrice.findFirst("meta[itemprop$=price]").map(_.attr("content").toInt)
    } yield Item(
      link = "https://avito.ru" + aWithLink.attr("href"),
      name = titleSpan.text(),
      time = time,
      cost = price
    )

  private val monthsToNum = Map(
    "январь" -> 1, "января" -> 1,
    "февраль" -> 2, "февраля" -> 2,
    "март" -> 3, "марта" -> 3,
    "апрель" -> 4, "апреля" -> 4,
    "май" -> 5, "мая" -> 5,
    "июнь" -> 6, "июня" -> 6,
    "июль" -> 7, "июля" -> 7,
    "август" -> 8, "августа" -> 8,
    "сентябрь" -> 9, "сентября" -> 9,
    "октябрь" -> 10, "октября" -> 10,
    "ноябрь" -> 11, "ноября" -> 11,
    "декабрь" -> 12, "декабря" -> 12
  )

  implicit class ElementOps(val element: Element) extends AnyVal {
    def findFirst(template: String): Either[String, Element] =
      Option(element.selectFirst(template)).fold(s"Not found: $template".asLeft[Element])(_.asRight[String])
  }

}
