package io.github.oybek.kraken.parser

import java.time.LocalDateTime

import cats.implicits.{catsStdInstancesForEither, catsSyntaxEitherId, toTraverseOps}
import cats.instances.list._
import io.github.oybek.kraken.domain.model.Item
import org.jsoup.nodes.Element

import scala.jdk.CollectionConverters._

package object avito {

  implicit val itemParser: Parser[List[Item]] = {
    case AvitoItem(document) =>
      for {
        aWithLink <- document.findFirst("a[data-marker$=item-title]")
        titleSpan <- aWithLink.findFirst("span")
        divWithDate <- document.findFirst("div[data-marker$=item-date]")
        dateSource = List(divWithDate.attr("data-tooltip").trim, divWithDate.text().trim)
        dateRegex = (s"""(\\d+) (${monthsToNum.keys.mkString("|")}) (\\d+):(\\d+)""").r
        minutesRegex = (s"""(\\d+) минут. назад""").r
        hoursRegex = (s"""(\\d+) час. назад""").r
        time <- dateSource.collectFirst {
          case dateRegex(dayOfMonth, month, hh, mm) =>
            LocalDateTime
              .now()
              .withMonth(monthsToNum.getOrElse(month, 1))
              .withDayOfMonth(dayOfMonth.toInt)
              .withHour(hh.toInt)
              .withMinute(mm.toInt)
              .withSecond(0)
              .withNano(0)
          case minutesRegex(minutes) =>
            LocalDateTime.now().minusMinutes(minutes.toInt)
          case hoursRegex(hours) =>
            LocalDateTime.now().minusHours(hours.toInt)
        }.fold(LocalDateTime.now().minusHours(6).asRight[String])(_.asRight[String])
        spanWithPrice <- document.findFirst("span[data-marker$=item-price]")
        price <- spanWithPrice.findFirst("meta[itemprop$=price]").map(_.attr("content").toInt)
      } yield List(Item(
        link = "https://avito.ru" + aWithLink.attr("href"),
        name = titleSpan.text(),
        time = time,
        cost = price
      ))
    case AvitoItemList(document) =>
      for {
        rawItems <- document.select("div[data-marker$=item]")
          .asScala
          .toList
          .asRight[String]
        res <- rawItems.map(AvitoItem).flatTraverse(itemParser.parse)
      } yield res
    case AvitoPage(document) =>
      for {
        itemList <- document.findFirst("div[data-marker$=catalog-serp]")
        res <- itemParser.parse(AvitoItemList(itemList))
      } yield res
  }

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
