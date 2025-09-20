package dev.kotlinssr.ui.pages

import dev.kotlinssr.data.model.PlayerData
import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.button
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.main
import kotlinx.html.p

fun FlowContent.PlayPage(playerData: PlayerData) {
    div {
        TopStatusBar(playerData)
        main {
            p {
                +"Main content"
            }
        }
        ShopSection()
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.TopStatusBar(playerData: PlayerData) {
    div(classes = "top-status-bar") {
        div(classes = "status-container") {
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
        button {
            attributes.hx {
                get = "/logout"
                target = "body"
                swap = HxSwap.outerHtml
            }
            +"Logout"
        }
    }
}

fun FlowContent.ShopSection() {
    div(classes = "shop-section") {
        p {
            +"shop section"
        }
    }
}
