package io.github.oybek.kraken.domain.model

sealed trait ItemEvent {
  def item: Item
}
case class ItemCreated(item: Item) extends ItemEvent
case class ItemChanged(prev: Item, item: Item) extends ItemEvent
case class ItemDeleted(item: Item) extends ItemEvent
