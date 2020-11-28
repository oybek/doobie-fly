package io.github.oybek.kraken.domain

import java.sql.Timestamp
import java.time.LocalDateTime

import io.github.oybek.kraken.domain.diff
import io.github.oybek.kraken.domain.model._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DiffSpec extends AnyFlatSpec with Matchers {

  "when new item, diff" must "return it" in {
    val items = List(
      Item("google.com", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100),
      Item("google.ru", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100)
    )
    val items2 = Item("google.com/123", "new cpu", Timestamp.valueOf(LocalDateTime.now()), 100) :: items

    diff(items, items2) should be(List(ItemCreated(items2.head)))
  }

  "when item deleted diff" must "return it" in {
    val items = List(
      Item("google.com", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100),
      Item("google.ru", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100)
    )
    val items2 = items.tail

    diff(items, items2) should be(List(ItemDeleted(items.head)))
  }

  "when item updated diff" must "return it" in {
    val items = List(
      Item("google.com", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100),
      Item("google.ru", "cpu", Timestamp.valueOf(LocalDateTime.now()), 100)
    )
    val items2 = items.map(x => x.copy(cost = x.cost * 2))

    diff(items, items2).toSet should be(
      items
        .zip(items2)
        .map {
          case (x, y) => ItemChanged(x, y)
        }
        .toSet
    )
  }
}
