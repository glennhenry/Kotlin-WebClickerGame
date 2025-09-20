package dev.kotlinssr.ui.pages

import dev.kotlinssr.data.model.PlayerData
import dev.kotlinssr.data.model.Upgrade
import dev.kotlinssr.data.model.allUpgrades
import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.http.parameters
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.px
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.main
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.span
import kotlinx.html.strong
import kotlinx.html.style
import kotlinx.html.unsafe
import kotlin.random.Random

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.PlayPage(playerData: PlayerData, currentPage: Int) {
    div {
        TopStatusBar(playerData)
        main(classes = "main-content") {
            button(classes = "click-me") {
                attributes.hx {
                    post = "/click?page=${currentPage}"
                    target = "#top-status-id, #upgrades-container"
                    swap = HxSwap.outerHtml
                }
                onClick = "clicked(${playerData.pointPerClick})"
                +"Click me!"
            }
        }
        ShopSection(currentPage)
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.TopStatusBar(playerData: PlayerData) {
    div(classes = "top-status-bar") {
        id = "top-status-id"
        div(classes = "stats-container") {
            div(classes = "stats-column-container") {
                p {
                    +"Total click points: ${playerData.clickPoints}"
                }
                p {
                    +"You have clicked for: ${playerData.totalClicks} times"
                }
            }
            div(classes = "stats-column-container") {
                p {
                    +"Click points on each click: ${playerData.pointPerClick}"
                }
            }
        }
        button(classes = "logout-button") {
            attributes.hx {
                get = "/logout"
                target = "body"
                swap = HxSwap.outerHtml
            }
            +"Logout"
        }
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.ShopSection(currentPage: Int) {
    div(classes = "shop-section") {
        div {
            id = "upgrades-container"
            // initially fetch page 1, served by /shop in apiRoutes
            attributes.hx {
                get = "/shop?page=$currentPage"
                // since shop display is fetched, further update can simply re-fetch
                trigger = "click from:.click-me, click from:.buy-button"
                target = "#upgrades-container"
                swap = HxSwap.innerHtml
                trigger = "load"
            }
        }
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.ShopCard(upgrade: Upgrade, canBuy: Boolean, bought: Boolean) {
    div(classes = "shop-card") {
        p("upgrade-title") {
            +upgrade.name
        }
        p("upgrade-description") {
            +upgrade.description
        }
        p("upgrade-cost") {
            strong { +"Cost:" }
            span(classes = "emphasized-text") { +"${upgrade.cost}" }
            +" click points to increase "
            span(classes = "emphasized-text") { +"${upgrade.clickPointIncrease} click points" }
        }

        val buttonText: String
        val isDisabled: Boolean
        val buttonClass: String

        when {
            bought -> {
                buttonText = "Bought"
                isDisabled = true
                buttonClass = "disabled-buy-button"
            }
            !canBuy -> {
                buttonText = "Not enough"
                isDisabled = true
                buttonClass = "disabled-buy-button"
            }
            else -> {
                buttonText = "Buy"
                isDisabled = false
                buttonClass = "buy-button"
            }
        }

        button(classes = buttonClass) {
            attributes.hx {
                get = "/buy"
                parameters {
                    set("name", upgrade.name)
                }
            }
            disabled = isDisabled
            p(classes = "buy-text") {
                +buttonText
            }
        }
    }
}

