package io.github.oybek.kraken

import io.github.oybek.kraken.parser.Parser
import org.jsoup.nodes.Document

package object avito {

  implicit val itemParser: Parser[Item] = (document: Document) => ???
}
