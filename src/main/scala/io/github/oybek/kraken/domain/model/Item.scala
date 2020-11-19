package io.github.oybek.kraken.domain.model

import java.time.LocalDateTime

case class Item(link: String,
                name: String,
                time: LocalDateTime,
                cost: Int)
