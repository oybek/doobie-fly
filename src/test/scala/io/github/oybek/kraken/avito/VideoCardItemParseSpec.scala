package io.github.oybek.kraken.avito

import java.time.LocalDateTime

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class VideoCardItemParseSpec extends ParseSpec {

  val item: Item = Item(
    link = "https://avito.ru/ekaterinburg/tovary_dlya_kompyutera/videokarta_msi_rx_550_2gb_aero_2044729083",
    name = "Видеокарта MSI RX 550 2gb aero",
    time = LocalDateTime.parse("2020-11-18T11:41:00"),
    cost = 2700
  )

  val itemDocument: Document = Jsoup.parse(
    """
      |<div class="snippet snippet-horizontal    item-snippet-with-aside item item_table clearfix js-catalog-item-enum item-with-contact js-item-extended" itemscope="" itemtype="http://schema.org/Product" id="i2044729083" data-item-id="2044729083" data-position="" data-marker="item" data-context="{&quot;stickyCatalogFilters&quot;:false,&quot;snippetDevelopmentLink&quot;:false,&quot;snippetRedesign&quot;:false,&quot;sendPhoneFromEmployerToChat&quot;:{&quot;enabled&quot;:false,&quot;exposure&quot;:&quot;&quot;},&quot;newSnippetForCompare&quot;:false,&quot;isReactCatalogSnippetEnabled&quot;:false,&quot;imageAspectRatio&quot;:&quot;4-3&quot;,&quot;verifiedBadgeRedesignRealty&quot;:null}">
      |   <meta itemprop="description" content="...">
      |   <div class="item__line">
      |      <div class="item-photo item-photo--4-3 " data-marker="item-photo">
      |         <a class="item-slider item-slider--4-3" href="/ekaterinburg/tovary_dlya_kompyutera/videokarta_msi_rx_550_2gb_aero_2044729083" target="_blank">
      |            <ul class="item-slider-list">
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://44.img.avito.st/image/1/1bUZjraBeVxvK4taP82drOYtfVq7LXla3E59Wm8ri1qvKXVYry95GA" class="large-picture-img" alt="Видеокарта MSI RX 550 2gb aero">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image" style="background-color: #f8f8f8">
      |                     <img src="https://46.img.avito.st/image/1/17wdaLaBe1VrzYlTPyufpeLLf1O_y3tT2Kh_U2vNiVOrz3dRq8l7EQ" class="large-picture-img" alt="Видеокарта MSI RX 550 2gb aero">
      |                  </div>
      |               </li>
      |            </ul>
      |         </a>
      |         <div>
      |            <div class="favorites-add is-design-simple js-favorites-add" data-marker="favorites-add" data-shape="default"><a class="favorites-add__link js-favorites-add-toggle" href="/favorites/add/2044729083" data-event-config="{&quot;id&quot;:2044729083,&quot;isFavorite&quot;:false,&quot;categorySlug&quot;:&quot;tovary_dlya_kompyutera&quot;,&quot;rootCategoryId&quot;:6,&quot;access&quot;:{&quot;favorite&quot;:true,&quot;compare&quot;:false},&quot;searchHash&quot;:&quot;31w9v3m4o84kgw4wwcwwkock0w4k84k&quot;,&quot;fromPage&quot;:&quot;catalog&quot;,&quot;fromBlock&quot;:null}" title="Добавить в избранное"><i class="i js-favorites-add-icon i-favorites-big i-favorites_filled-blue" data-state="empty" data-root-category-id=""></i><span class="favorites-add__label pseudo-link js-favorites-add-label">В избранное</span></a></div>
      |         </div>
      |      </div>
      |      <div class="item_table-wrapper">
      |         <div class="description item_table-description">
      |            <div class="snippet-title-row">
      |               <h3 class="snippet-title" data-shape="default"><a class="snippet-link" itemprop="url" data-marker="item-title" href="/ekaterinburg/tovary_dlya_kompyutera/videokarta_msi_rx_550_2gb_aero_2044729083" target="_blank" title="Видеокарта MSI RX 550 2gb aero в Екатеринбурге"><span itemprop="name" class="snippet-link-name">
      |                  Видеокарта MSI RX 550 2gb aero
      |                  </span></a>
      |               </h3>
      |            </div>
      |            <div class="snippet-price-row">
      |               <span itemprop="offers" itemtype="http://schema.org/Offer" itemscope="" class="snippet-price " data-shape="default" data-marker="item-price">
      |                  <meta itemprop="priceCurrency" content="RUB">
      |                  <meta itemprop="price" content="2700">
      |                  <meta itemprop="availability" content="https://schema.org/LimitedAvailability">
      |                  2 700  ₽
      |               </span>
      |            </div>
      |            <div class="snippet-line-row"><span class="snippet-text" data-color="gray44" data-shape="default">
      |               Товары для компьютера
      |               </span>
      |            </div>
      |            <div class="item-notes-wrapper item-notes-wrapper_serp">
      |               <div data-item-id="2044729083" data-user-id="120196998" data-category-id="101" data-category="tovary_dlya_kompyutera" data-vertical="" data-city-name="Екатеринбург" data-region-name="R13" class="item-view-notes js-item-view-notes hidden"></div>
      |            </div>
      |            <div class="color-gray44">
      |               <div class="item-address" data-shape="default">
      |                  <div itemprop="address" itemscope="" itemtype="http://schema.org/PostalAddress">
      |                     <div class="item-address-georeferences"><span class="item-address-georeferences"><span class="item-address-georeferences-item"><span class="item-address-georeferences-item-icons"><i class="item-address-georeferences-item-icons__icon" style="background-color: #0A6F20"></i></span><span class="item-address-georeferences-item__content">Геологическая</span><span>,</span><span class="item-address-georeferences-item__after"> 1&nbsp;км</span></span></span></div>
      |                     <meta itemprop="addressLocality" content="Екатеринбург">
      |                  </div>
      |               </div>
      |            </div>
      |            <div class="snippet-date-row">
      |               <div class="snippet-date-info" data-marker="item-date" data-shape="default" data-tooltip="18 ноября 11:41" flow="down">
      |                  1 час назад
      |               </div>
      |            </div>
      |         </div>
      |         <div class="item_table-aside">
      |            <div class="js-item_table-extended-contacts snippet-contacts item_table-extended-contacts item_table-extended-contacts_transparent" aria-orientation="horizontal">
      |               <div class="js-item-contacts-button item-extended-contacts" data-marker="item-contact" id="2044729083" data-props="{&quot;itemId&quot;:2044729083,&quot;buttonText&quot;:&quot;Показать телефон&quot;,&quot;searchHash&quot;:&quot;31w9v3m4o84kgw4wwcwwkock0w4k84k&quot;,&quot;fromBlock&quot;:0,&quot;vsrc&quot;:&quot;s&quot;,&quot;loginLink&quot;:&quot;#login?authsrc=ps&amp;next=\/ekaterinburg\/tovary_dlya_kompyutera\/videokarta_msi_rx_550_2gb_aero_2044729083?showPhoneNumber&quot;,&quot;authLink&quot;:&quot;#login?authsrc=ps&quot;,&quot;urlPath&quot;:&quot;\/ekaterinburg\/tovary_dlya_kompyutera\/videokarta_msi_rx_550_2gb_aero_2044729083&quot;,&quot;signin&quot;:0,&quot;hasCVPackage&quot;:0,&quot;categoryRootId&quot;:6}"><button class="button button-origin" style="padding: 6px 12px;">
      |                  Показать телефон
      |                  </button>
      |               </div>
      |               <div class="item-extended-contacts-messenger"><span class="js-messenger-button" data-props="{&quot;isOpenInNewTab&quot;:null,&quot;isAuthorized&quot;:true,&quot;isFullWidth&quot;:true,&quot;isMiniMessenger&quot;:true,&quot;isIconButton&quot;:null,&quot;isHidden&quot;:true,&quot;parentSelector&quot;:&quot;.item-with-contact&quot;,&quot;hasHidePhoneOnboarding&quot;:null,&quot;sellerIdHash&quot;:null,&quot;itemId&quot;:2044729083,&quot;itemUrl&quot;:&quot;\/ekaterinburg\/tovary_dlya_kompyutera\/videokarta_msi_rx_550_2gb_aero_2044729083&quot;,&quot;itemCVViewed&quot;:null,&quot;categoryId&quot;:101,&quot;buttonText&quot;:&quot;Написать&quot;,&quot;replyTime&quot;:null,&quot;logParams&quot;:{&quot;wsrc&quot;:&quot;s&quot;,&quot;s&quot;:&quot;mi&quot;},&quot;experiments&quot;:[],&quot;side&quot;:null,&quot;contactlessView&quot;:null}" data-marker="messenger-button" data-side=""><a class="button button-origin contactBar_blueColor button-origin_full-width" style="">
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
