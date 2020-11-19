package io.github.oybek.kraken.domain

import io.github.oybek.kraken.avito.Item

sealed trait ItemEvent
case class ItemCreated(item: Item) extends ItemEvent
case class ItemChanged(prev: Item, curr: Item) extends ItemEvent
case class ItemDeleted(item: Item) extends ItemEvent
