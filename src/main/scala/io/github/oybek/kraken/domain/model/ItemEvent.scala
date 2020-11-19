package io.github.oybek.kraken.domain.model

sealed trait ItemEvent
case class ItemCreated(item: Item) extends ItemEvent
case class ItemChanged(prev: Item, curr: Item) extends ItemEvent
case class ItemDeleted(item: Item) extends ItemEvent
