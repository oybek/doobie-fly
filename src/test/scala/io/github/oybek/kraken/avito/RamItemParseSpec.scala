package io.github.oybek.kraken.avito

import java.time.LocalDateTime

import cats.implicits.catsSyntaxEitherId
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class RamItemParseSpec extends ParseSpec {

  val item: Either[String, Item] = Item(
    link = "https://avito.ru/ekaterinburg/tovary_dlya_kompyutera/operativnaya_pamyat_8gb_x2_16gb_ddr4_2017375313",
    name = "Оперативная память 8gb x2 16gb ddr4",
    time = LocalDateTime.parse("2020-11-18T10:10:00"),
    cost = 5000
  ).asRight[String]

  val itemDocument: Document = Jsoup.parse(
    """
      |<div class="snippet snippet-horizontal    item-snippet-with-aside item item_table clearfix js-catalog-item-enum item-with-contact js-item-extended" itemscope="" itemtype="http://schema.org/Product" id="i2017375313" data-item-id="2017375313" data-position="" data-marker="item" data-context="{&quot;stickyCatalogFilters&quot;:false,&quot;snippetDevelopmentLink&quot;:false,&quot;snippetRedesign&quot;:false,&quot;sendPhoneFromEmployerToChat&quot;:{&quot;enabled&quot;:false,&quot;exposure&quot;:&quot;&quot;},&quot;newSnippetForCompare&quot;:false,&quot;isReactCatalogSnippetEnabled&quot;:false,&quot;imageAspectRatio&quot;:&quot;4-3&quot;,&quot;verifiedBadgeRedesignRealty&quot;:null}">
      |   <meta itemprop="description" content="...">
      |   <div class="item__line">
      |      <div class="item-photo item-photo--4-3 " data-marker="item-photo">
      |         <a class="item-slider item-slider--4-3" href="/ekaterinburg/tovary_dlya_kompyutera/operativnaya_pamyat_8gb_x2_16gb_ddr4_2017375313" target="_blank">
      |            <ul class="item-slider-list">
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image">
      |                     <img srcset="https://96.img.avito.st/208x156/9917853896.jpg 1x, https://96.img.avito.st/image/1/FYboWba_uW-e_EtpzjkInxf6v2lQ_LtlVjq9nVr6u29cvA 1.5x" src="https://96.img.avito.st/208x156/9917853896.jpg" class="large-picture-img" alt="Оперативная память 8gb x2 16gb ddr4">
      |                  </div>
      |               </li>
      |               <li class="item-slider-item">
      |                  <div class="item-slider-image">
      |                     <img srcset="https://55.img.avito.st/208x156/9917853855.jpg 1x, https://55.img.avito.st/image/1/QjELV7a_7th98hzeAzZfKPT06N6z8uzStTTqKrn07Ni_sg 1.5x" src="https://55.img.avito.st/208x156/9917853855.jpg" class="large-picture-img" alt="Оперативная память 8gb x2 16gb ddr4">
      |                  </div>
      |               </li>
      |            </ul>
      |         </a>
      |         <div>
      |            <div class="favorites-add is-design-simple js-favorites-add" data-marker="favorites-add" data-shape="default"><a class="favorites-add__link js-favorites-add-toggle" href="/favorites/add/2017375313" data-event-config="{&quot;id&quot;:2017375313,&quot;isFavorite&quot;:false,&quot;categorySlug&quot;:&quot;tovary_dlya_kompyutera&quot;,&quot;rootCategoryId&quot;:6,&quot;access&quot;:{&quot;favorite&quot;:true,&quot;compare&quot;:false},&quot;searchHash&quot;:&quot;gti4pygwf1cg8kc8888s8co4w0cg008&quot;,&quot;fromPage&quot;:&quot;catalog&quot;,&quot;fromBlock&quot;:null}" title="Добавить в избранное"><i class="i js-favorites-add-icon i-favorites-big i-favorites_filled-blue" data-state="empty" data-root-category-id=""></i><span class="favorites-add__label pseudo-link js-favorites-add-label">В избранное</span></a></div>
      |         </div>
      |      </div>
      |      <div class="item_table-wrapper">
      |         <div class="description item_table-description">
      |            <div class="snippet-title-row">
      |               <h3 class="snippet-title" data-shape="default">
      |               <a class="snippet-link" itemprop="url" data-marker="item-title" href="/ekaterinburg/tovary_dlya_kompyutera/operativnaya_pamyat_8gb_x2_16gb_ddr4_2017375313" target="_blank" title="Оперативная память 8gb x2 16gb ddr4 в Екатеринбурге">
      |                 <span itemprop="name" class="snippet-link-name">
      |                  Оперативная память 8gb x2 16gb ddr4
      |                 </span>
      |               </a>
      |               </h3>
      |            </div>
      |            <div class="snippet-price-row">
      |               <span itemprop="offers" itemtype="http://schema.org/Offer" itemscope="" class="snippet-price " data-shape="default" data-marker="item-price">
      |                  <meta itemprop="priceCurrency" content="RUB">
      |                  <meta itemprop="price" content="5000">
      |                  <meta itemprop="availability" content="https://schema.org/LimitedAvailability">
      |                  5 000  ₽
      |               </span>
      |            </div>
      |            <div class="snippet-line-row"><span class="snippet-text" data-color="gray44" data-shape="default">
      |               Товары для компьютера
      |               </span>
      |            </div>
      |            <div class="snippet-line-row"><span class="snippet-text" data-color="gray44" data-shape="default">
      |               Компания
      |               </span>
      |            </div>
      |            <div class="item-notes-wrapper item-notes-wrapper_serp">
      |               <div data-item-id="2017375313" data-user-id="120196998" data-category-id="101" data-category="tovary_dlya_kompyutera" data-vertical="" data-city-name="Екатеринбург" data-region-name="R13" class="item-view-notes js-item-view-notes hidden"></div>
      |            </div>
      |            <div class="color-gray44">
      |               <div class="item-address" data-shape="default">
      |                  <div itemprop="address" itemscope="" itemtype="http://schema.org/PostalAddress">
      |                     <div class="item-address-georeferences"><span class="item-address-georeferences"><span class="item-address-georeferences-item"><span class="item-address-georeferences-item-icons"><i class="item-address-georeferences-item-icons__icon" style="background-color: #0A6F20"></i></span><span class="item-address-georeferences-item__content">Ботаническая</span><span>,</span><span class="item-address-georeferences-item__after"> 400&nbsp;м</span></span></span></div>
      |                     <meta itemprop="addressLocality" content="Екатеринбург">
      |                  </div>
      |               </div>
      |            </div>
      |            <div class="snippet-date-row">
      |               <div class="snippet-date-info" data-marker="item-date" data-shape="default" data-tooltip="18 ноября 10:10" flow="down">
      |                  4 часа назад
      |               </div>
      |            </div>
      |         </div>
      |         <div class="item_table-aside">
      |            <div class="js-item_table-extended-contacts snippet-contacts item_table-extended-contacts item_table-extended-contacts_transparent item_gallery-extended-contacts_transparent" aria-orientation="horizontal">
      |               <div class="js-item-contacts-button item-extended-contacts" data-marker="item-contact" id="2017375313" data-props="{&quot;itemId&quot;:2017375313,&quot;buttonText&quot;:&quot;Показать телефон&quot;,&quot;searchHash&quot;:&quot;gti4pygwf1cg8kc8888s8co4w0cg008&quot;,&quot;fromBlock&quot;:0,&quot;vsrc&quot;:&quot;s&quot;,&quot;loginLink&quot;:&quot;#login?authsrc=ps&amp;next=\/ekaterinburg\/tovary_dlya_kompyutera\/operativnaya_pamyat_8gb_x2_16gb_ddr4_2017375313?showPhoneNumber&quot;,&quot;authLink&quot;:&quot;#login?authsrc=ps&quot;,&quot;urlPath&quot;:&quot;\/ekaterinburg\/tovary_dlya_kompyutera\/operativnaya_pamyat_8gb_x2_16gb_ddr4_2017375313&quot;,&quot;signin&quot;:0,&quot;hasCVPackage&quot;:0,&quot;categoryRootId&quot;:6}">
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
