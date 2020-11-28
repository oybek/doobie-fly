package io.github.oybek.kraken.domain.model

import java.sql.Timestamp

case class Item(link: String,
                name: String,
                time: Timestamp,
                cost: Int)

