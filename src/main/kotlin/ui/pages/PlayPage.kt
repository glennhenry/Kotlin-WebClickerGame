package dev.kotlinssr.ui.pages

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.button
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.main
import kotlinx.html.p

fun FlowContent.PlayPage() {
    div {
        TopStatusBar()
        main {
            p {
                +"Main content"
            }
        }
        ShopSection()
    }
}

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.TopStatusBar() {
    div(classes = "top-status-bar") {
        p {
            +"status bar"
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
