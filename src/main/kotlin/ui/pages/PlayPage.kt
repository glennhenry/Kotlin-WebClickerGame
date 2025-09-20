package dev.kotlinssr.ui.pages

import dev.kotlinssr.data.model.PlayerData
import dev.kotlinssr.data.model.Upgrade
import dev.kotlinssr.data.model.exampleUpgrades
import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.http.parameters
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.button
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.main
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.strong

fun FlowContent.PlayPage(playerData: PlayerData) {
    div {
        TopStatusBar(playerData)
        main {
            p {
                +"Main content"
            }
        }
        ShopSection(playerData.upgrades)
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

fun FlowContent.ShopSection(playerUpgrades: List<Upgrade>) {
    div(classes = "shop-section") {
        exampleUpgrades.forEach {
            ShopCard(it, bought = playerUpgrades.contains(it))
        }
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.ShopCard(upgrade: Upgrade, bought: Boolean) {
    div(classes = "shop-card") {
        p("upgrade-title") {
            +upgrade.name
        }
        p("upgrade-description") {
            +upgrade.description
        }
        p("upgrade-cost") {
            strong { +"Cost:" }
            span(classes = "emphasized-text") { +"${upgrade.cost}"}
            +"click point to increase "
            span(classes = "emphasized-text") { +"${upgrade.clickPointIncrease} click point"}
        }
        button(classes = if (bought) "disabled-buy-button" else "buy-button") {
            attributes.hx {
                get = "/buy"
                parameters {
                    set("name", upgrade.name)
                }
            }
            disabled = bought
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
