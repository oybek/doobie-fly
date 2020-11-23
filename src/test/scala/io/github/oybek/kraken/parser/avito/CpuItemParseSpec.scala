package io.github.oybek.kraken.parser.avito

import java.time.LocalDateTime

import cats.implicits.catsSyntaxEitherId
import io.github.oybek.kraken.domain.model.Item
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class CpuItemParseSpec extends ParseSpec {

  val items: Either[String, List[Item]] = List(Item(
    link = "https://avito.ru/ekaterinburg/tovary_dlya_kompyutera/ryzen_5_2600_am4_2040986151",
    name = "Ryzen 5 2600 ; am4",
    time = LocalDateTime.parse("2020-11-17T18:20:00"),
    cost = 9000
  )).asRight[String]

  val itemDocument: Document = Jsoup.parse(
    """
      |<div class="snippet snippet-horizontal    item-snippet-with-aside item item_table clearfix js-catalog-item-enum item-with-contact js-item-extended" itemscope="" itemtype="http://schema.org/Product" id="i2040986151" data-item-id="2040986151" data-position="" data-marker="item" data-context="{&quot;stickyCatalogFilters&quot;:false,&quot;snippetDevelopmentLink&quot;:false,&quot;snippetRedesign&quot;:false,&quot;sendPhoneFromEmployerToChat&quot;:{&quot;enabled&quot;:false,&quot;exposure&quot;:&quot;&quot;},&quot;newSnippetForCompare&quot;:false,&quot;isReactCatalogSnippetEnabled&quot;:false,&quot;imageAspectRatio&quot;:&quot;4-3&quot;,&quot;verifiedBadgeRedesignRealty&quot;:null}">
      |   <meta itemprop="description" content="...">
      |   <div class="item__line">
      |      <div class="item-photo item-photo--4-3 " data-marker="item-photo">
      |         <a class="photo-wrapper js-item-link js-photo-wrapper large-picture large-picture--4-3" href="/ekaterinburg/tovary_dlya_kompyutera/ryzen_5_2600_am4_2040986151" target="_blank" title="Объявление «Ryzen 5 2600 ; am4» с фотографией">
      |         <img src="https://15.img.avito.st/image/1/UoOsAraB_mrapwxshFMBhlOh-mwOof5sacL6bNqnDGwapfJuGqP-Lg" class="photo-count-show large-picture-img" alt="Ryzen 5 2600 ; am4">
      |         </a>
      |         <div>
      |            <div class="favorites-add is-design-simple js-favorites-add" data-marker="favorites-add" data-shape="default"><a class="favorites-add__link js-favorites-add-toggle" href="/favorites/add/2040986151" data-event-config="{&quot;id&quot;:2040986151,&quot;isFavorite&quot;:false,&quot;categorySlug&quot;:&quot;tovary_dlya_kompyutera&quot;,&quot;rootCategoryId&quot;:6,&quot;access&quot;:{&quot;favorite&quot;:true,&quot;compare&quot;:false},&quot;searchHash&quot;:&quot;lhtldfuf0dw8kw04kc4oc0ko8w4gk88&quot;,&quot;fromPage&quot;:&quot;catalog&quot;,&quot;fromBlock&quot;:null}" title="Добавить в избранное"><i class="i js-favorites-add-icon i-favorites-big i-favorites_filled-blue" data-state="empty" data-root-category-id=""></i><span class="favorites-add__label pseudo-link js-favorites-add-label">В избранное</span></a></div>
      |         </div>
      |      </div>
      |      <div class="item_table-wrapper">
      |         <div class="description item_table-description">
      |            <div class="snippet-title-row">
      |               <h3 class="snippet-title" data-shape="default"><a class="snippet-link" itemprop="url" data-marker="item-title" href="/ekaterinburg/tovary_dlya_kompyutera/ryzen_5_2600_am4_2040986151" target="_blank" title="Ryzen 5 2600 ; am4 в Екатеринбурге"><span itemprop="name" class="snippet-link-name">
      |                  Ryzen 5 2600 ; am4
      |                  </span></a>
      |               </h3>
      |            </div>
      |            <div class="snippet-price-row">
      |               <span itemprop="offers" itemtype="http://schema.org/Offer" itemscope="" class="snippet-price " data-shape="default" data-marker="item-price">
      |                  <meta itemprop="priceCurrency" content="RUB">
      |                  <meta itemprop="price" content="9000">
      |                  <meta itemprop="availability" content="https://schema.org/LimitedAvailability">
      |                  9 000  ₽
      |               </span>
      |               <span style="display: inline-block; width: 6px;"></span>
      |               <div class="js-catalog-delivery snippet-delivery" data-item-id="2040986151" data-shape="default">
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
      |               <div data-item-id="2040986151" data-user-id="120196998" data-category-id="101" data-category="tovary_dlya_kompyutera" data-vertical="" data-city-name="Екатеринбург" data-region-name="R13" class="item-view-notes js-item-view-notes hidden"></div>
      |            </div>
      |            <div class="color-gray44">
      |               <div class="item-address" data-shape="default">
      |                  <div itemprop="address" itemscope="" itemtype="http://schema.org/PostalAddress">
      |                     <div class="item-address-georeferences"><span class="item-address-georeferences"><span class="item-address-georeferences-item"><span class="item-address-georeferences-item__content">р-н Кировский</span></span></span></div>
      |                     <meta itemprop="addressLocality" content="Екатеринбург">
      |                  </div>
      |               </div>
      |            </div>
      |            <div class="snippet-date-row">
      |               <div class="snippet-date-info" data-marker="item-date" data-shape="default" data-tooltip="17 ноября 18:20" flow="down">
      |                  19 часов назад
      |               </div>
      |            </div>
      |         </div>
      |         <div class="item_table-aside">
      |            <div class="js-item_table-extended-contacts snippet-contacts item_table-extended-contacts item_table-extended-contacts_transparent item_gallery-extended-contacts_transparent" aria-orientation="horizontal">
      |               <div class="js-item-contacts-button item-extended-contacts" data-marker="item-contact" id="2040986151" data-props="{&quot;itemId&quot;:2040986151,&quot;buttonText&quot;:&quot;Показать телефон&quot;,&quot;searchHash&quot;:&quot;lhtldfuf0dw8kw04kc4oc0ko8w4gk88&quot;,&quot;fromBlock&quot;:0,&quot;vsrc&quot;:&quot;s&quot;,&quot;loginLink&quot;:&quot;#login?authsrc=ps&amp;next=\/ekaterinburg\/tovary_dlya_kompyutera\/ryzen_5_2600_am4_2040986151?showPhoneNumber&quot;,&quot;authLink&quot;:&quot;#login?authsrc=ps&quot;,&quot;urlPath&quot;:&quot;\/ekaterinburg\/tovary_dlya_kompyutera\/ryzen_5_2600_am4_2040986151&quot;,&quot;signin&quot;:0,&quot;hasCVPackage&quot;:0,&quot;categoryRootId&quot;:6}">
      |                  <div class="phone-button-root-32d_G">
      |                     <button type="button" data-marker="contacts-button/button-undefined" class="button-button-3p4ra button-button-2Fo5k button-size-s-3-rn6 button-default-mSfac" aria-busy="false">
      |                        <span class="button-textBox-Row9K">
      |                           <div class="button-button__text_wrapper-32znN"><span class="text-text-1PdBw text-size-s-1PUdo">Показать телефон</span></div>
      |                        </span>
      |                     </button>
      |                  </div>
      |               </div>
      |               <div class="item-extended-contacts-messenger">
      |                  <span class="js-messenger-button" data-marker="messenger-button" data-side="">
      |                     <div class="messenger-button-root-3gcie messenger-button-root_fullwidth-1Qoze messenger-button-root_header-2wd2a">
      |                        <button data-marker="messenger-button/button" class="button-button-2Fo5k button-size-s-3-rn6 button-default-mSfac width-width-12-2VZLz" aria-busy="false">
      |                           <span class="button-textBox-Row9K">
      |                              <div class="">Написать</div>
      |                           </span>
      |                        </button>
      |                     </div>
      |                  </span>
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
