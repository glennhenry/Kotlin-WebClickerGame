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

fun FlowContent.PlayPage(playerData: PlayerData) {
    div {
        TopStatusBar(playerData)
        main(classes = "main-content") {
            button(classes = "click-me") {
                onClick = "clicked(${playerData.pointPerClick})"
                +"Click me!"
            }
        }
        ShopSection()
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.TopStatusBar(playerData: PlayerData) {
    div(classes = "top-status-bar") {
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
fun FlowContent.ShopSection() {
    div(classes = "shop-section") {
        div {
            id = "upgrades-container"
            // initially fetch page 1, served by /shop in apiRoutes
            attributes.hx {
                get = "/shop?page=1"
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
            +"click point to increase "
            span(classes = "emphasized-text") { +"${upgrade.clickPointIncrease} click point" }
        }
        button(classes = if (bought) "disabled-buy-button" else "buy-button") {
            attributes.hx {
                get = "/buy"
                parameters {
                    set("name", upgrade.name)
                }
            }
            disabled = bought || !canBuy
            p(classes = "buy-text") {
                if (bought) {
                    +"Bought"
                } else {
                    +"Buy"
                }
            }
        }
    }
}
