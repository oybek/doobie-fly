package io.github.oybek.kraken.avito

import java.time.LocalDateTime

case class Item(link: String,
                name: String,
                time: LocalDateTime,
                cost: Int)
