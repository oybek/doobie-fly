package io.github.oybek.kraken.parser.avito

import java.time.LocalDateTime

import cats.implicits.catsSyntaxEitherId
import io.github.oybek.kraken.domain.model.Item
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MotherboardItemParseSpec extends ParseSpec {

  val item: Either[String, Item] = Item(
    link = "https://avito.ru/ekaterinburg/tovary_dlya_kompyutera/materinskaya_plata_gigabyte_b450_aorus_m_1972334828",
    name = "Материнская плата gigabyte B450 aorus M",
    time = LocalDateTime.parse("2020-10-21T19:34:00"),
    cost = 5500
  ).asRight[String]

  val itemDocument: Document = Jsoup.parse(
    """
      |<div class="snippet snippet-horizontal    item-snippet-with-aside item item_table clearfix js-catalog-item-enum item-with-contact js-item-extended" itemscope="" itemtype="http://schema.org/Product" id="i1972334828" data-item-id="1972334828" data-position="" data-marker="item" data-context="{&quot;stickyCatalogFilters&quot;:false,&quot;snippetDevelopmentLink&quot;:false,&quot;snippetRedesign&quot;:false,&quot;sendPhoneFromEmployerToChat&quot;:{&quot;enabled&quot;:false,&quot;exposure&quot;:&quot;&quot;},&quot;newSnippetForCompare&quot;:false,&quot;isReactCatalogSnippetEnabled&quot;:false,&quot;imageAspectRatio&quot;:&quot;4-3&quot;,&quot;favoriteIconRedesign&quot;:&quot;&quot;,&quot;verifiedBadgeRedesignRealty&quot;:null}">
      |   <meta itemprop="description" content="...">
      |   <div class="item__line">
      |      <div class="item-photo item-photo--4-3 " data-marker="item-photo">
      |         <a class="item-slider item-slider--4-3" href="/ekaterinburg/tovary_dlya_kompyutera/materinskaya_plata_gigabyte_b450_aorus_m_1972334828" target="_blank">
      |            <ul class="item-slider-list">
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://11.img.avito.st/image/1/Tkl4VbaB4qAO8BCmaDg_B4z25qba9uKmvZXmpg7wEKbO8u6kzvTi5A" class="large-picture-img" alt="Материнская плата gigabyte B450 aorus M">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://14.img.avito.st/image/1/S7Jod7aB51se0hVdcho6_JzU413K1OddrbfjXR7SFV3e0Otf3tbnHw" class="large-picture-img" alt="Материнская плата gigabyte B450 aorus M">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://17.img.avito.st/image/1/RNBsKLaB6DkajRo_aEU1npiL7D_Oi-g_qejsPxqNGj_aj-Q92onofQ" class="large-picture-img" alt="Материнская плата gigabyte B450 aorus M">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://19.img.avito.st/image/1/RiVAAbaB6sw2pBjKQGw3a7Si7sriourKhcHuyjakGMr2pubI9qDqiA" class="large-picture-img" alt="Материнская плата gigabyte B450 aorus M">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://98.img.avito.st/image/1/Wz_vfbaB99aZ2AXQ1RAqcRve89BN3vfQKr3z0JnYBdBZ2vvSWdz3kg" class="large-picture-img" alt="Материнская плата gigabyte B450 aorus M">
      |                  </div>
      |                  <div class="item-slider-more">
      |                     Ещё<br>
      |                     2 фото
      |                  </div>
      |               </li>
      |            </ul>
      |         </a>
      |         <div>
      |            <div class="favorites-add is-design-simple js-favorites-add snippet-favorites-" data-marker="favorites-add" data-shape="default"><a class="favorites-add__link js-favorites-add-toggle" href="/favorites/add/1972334828" data-event-config="{&quot;id&quot;:1972334828,&quot;isFavorite&quot;:false,&quot;categorySlug&quot;:&quot;tovary_dlya_kompyutera&quot;,&quot;rootCategoryId&quot;:6,&quot;access&quot;:{&quot;favorite&quot;:true,&quot;compare&quot;:false},&quot;searchHash&quot;:&quot;5k90tylomgg8sosw848gsg8w4gocw8k&quot;,&quot;fromPage&quot;:&quot;catalog&quot;,&quot;fromBlock&quot;:null}" title="Добавить в избранное"><i class="i js-favorites-add-icon i-favorites-big i-favorites_filled-blue" data-state="empty" data-root-category-id=""></i><span class="favorites-add__label pseudo-link js-favorites-add-label">В избранное</span></a></div>
      |         </div>
      |      </div>
      |      <div class="item_table-wrapper">
      |         <div class="description item_table-description">
      |            <div class="snippet-title-row">
      |               <h3 class="snippet-title" data-shape="default"><a class="snippet-link" itemprop="url" data-marker="item-title" href="/ekaterinburg/tovary_dlya_kompyutera/materinskaya_plata_gigabyte_b450_aorus_m_1972334828" target="_blank" title="Материнская плата gigabyte B450 aorus M в Екатеринбурге"><span itemprop="name" class="snippet-link-name">
      |                  Материнская плата gigabyte B450 aorus M
      |                  </span></a>
      |               </h3>
      |            </div>
      |            <div class="snippet-price-row">
      |               <span itemprop="offers" itemtype="http://schema.org/Offer" itemscope="" class="snippet-price " data-shape="default" data-marker="item-price">
      |                  <meta itemprop="priceCurrency" content="RUB">
      |                  <meta itemprop="price" content="5500">
      |                  <meta itemprop="availability" content="https://schema.org/LimitedAvailability">
      |                  5 500  ₽
      |               </span>
      |               <span style="display: inline-block; width: 6px;"></span>
      |               <div class="js-catalog-delivery snippet-delivery" data-item-id="1972334828" data-shape="default">
      |                  <div class="root-root-s4DkA">
      |                     <svg width="20" height="18" xmlns="http://www.w3.org/2000/svg">
      |                        <g fill="none" fill-rule="evenodd">
      |                           <path class="root-icon-OWUTg" d="M17.462 13c.025.132.038.267.038.406 0 1.182-.933 2.14-2.083 2.14-1.151 0-2.084-.958-2.084-2.14 0-.139.013-.274.038-.406H6.63c.024.132.037.267.037.406 0 1.182-.933 2.14-2.084 2.14-1.15 0-2.083-.958-2.083-2.14 0-.139.013-.274.038-.406H.917A.917.917 0 0 1 0 12.083V1.917C0 1.41.41 1 .917 1h10.166c.507 0 .917.41.917.917V10h1V5.917c0-.507.41-.917.917-.917h1.734c.232 0 .454.087.624.245l3.432 3.183A.917.917 0 0 1 20 9.1v2.983c0 .507-.41.917-.917.917h-1.62z"></path>
      |                        </g>
      |                     </svg>
      |                  </div>
      |               </div>
      |            </div>
      |            <div class="item-notes-wrapper item-notes-wrapper_serp">
      |               <div data-item-id="1972334828" data-user-id="120196998" data-category-id="101" data-category="tovary_dlya_kompyutera" data-vertical="" data-city-name="Екатеринбург" data-region-name="R13" class="item-view-notes js-item-view-notes hidden"></div>
      |            </div>
      |            <div class="color-gray44">
      |               <div class="item-address" data-shape="default">
      |                  <div itemprop="address" itemscope="" itemtype="http://schema.org/PostalAddress">
      |                     <div class="item-address-georeferences"><span class="item-address-georeferences"><span class="item-address-georeferences-item"><span class="item-address-georeferences-item-icons"><i class="item-address-georeferences-item-icons__icon" style="background-color: #0A6F20"></i></span><span class="item-address-georeferences-item__content">Площадь 1905 года</span><span>,</span><span class="item-address-georeferences-item__after"> 200&nbsp;м</span></span></span></div>
      |                     <meta itemprop="addressLocality" content="Екатеринбург">
      |                  </div>
      |               </div>
      |            </div>
      |            <div class="snippet-date-row">
      |               <div class="snippet-date-info" data-marker="item-date" data-shape="default" data-tooltip="" flow="down">
      |                  21 октября 19:34
      |               </div>
      |            </div>
      |         </div>
      |         <div class="item_table-aside">
      |            <div class="js-item_table-extended-contacts snippet-contacts item_table-extended-contacts item_table-extended-contacts_transparent" aria-orientation="horizontal">
      |               <div class="item-extended-contacts-messenger"><span class="js-messenger-button" data-props="{&quot;isOpenInNewTab&quot;:null,&quot;isAuthorized&quot;:true,&quot;isFullWidth&quot;:true,&quot;isMiniMessenger&quot;:true,&quot;isIconButton&quot;:null,&quot;isHidden&quot;:true,&quot;parentSelector&quot;:&quot;.item-with-contact&quot;,&quot;hasHidePhoneOnboarding&quot;:null,&quot;sellerIdHash&quot;:null,&quot;itemId&quot;:1972334828,&quot;itemUrl&quot;:&quot;\/ekaterinburg\/tovary_dlya_kompyutera\/materinskaya_plata_gigabyte_b450_aorus_m_1972334828&quot;,&quot;itemCVViewed&quot;:null,&quot;categoryId&quot;:101,&quot;buttonText&quot;:&quot;Написать&quot;,&quot;replyTime&quot;:null,&quot;logParams&quot;:{&quot;wsrc&quot;:&quot;s&quot;,&quot;s&quot;:&quot;mi&quot;},&quot;experiments&quot;:[],&quot;side&quot;:null,&quot;contactlessView&quot;:null}" data-marker="messenger-button" data-side=""><a class="button button-origin contactBar_blueColor button-origin_full-width" style="">
      |                  Написать
      |                  </a></span>
      |               </div>
      |            </div>
      |         </div>
      |      </div>
      |      <div>
      |         <div class="snippet-vas js-vas-list-container" data-shape="default"></div>
      |      </div>
      |   </div>
      |</div>
      |""".stripMargin
  )
}
