package io.github.oybek.kraken

import io.github.oybek.kraken.domain.model._

package object domain {

  def diff(l1: List[Item], l2: List[Item]): List[ItemEvent] = {
    val m1 = l1.map(x => x.link -> x).toMap
    val m2 = l2.map(x => x.link -> x).toMap
    (l1 ++ l2).foldLeft(List.empty[ItemEvent]) { (events, item) =>
      (m1.get(item.link), m2.get(item.link)) match {
        case (Some(i1), Some(i2)) if i1 != i2 => ItemChanged(i1, i2) :: events
        case (Some(i1), None)                 => ItemDeleted(i1) :: events
        case (None, Some(i2))                 => ItemCreated(i2) :: events
        case _                                => events
      }
    }
  }
}
