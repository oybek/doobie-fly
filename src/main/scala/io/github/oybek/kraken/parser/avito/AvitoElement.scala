package io.github.oybek.kraken.parser.avito

import org.jsoup.nodes.Element

sealed trait AvitoElement
case class AvitoItem(document: Element) extends AvitoElement
case class AvitoItemList(document: Element) extends AvitoElement
case class AvitoPage(document: Element) extends AvitoElement
