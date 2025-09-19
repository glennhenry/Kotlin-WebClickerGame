package dev.kotlinssr.ui.pages

import kotlinx.html.FlowContent
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

fun FlowContent.TopStatusBar() {
    div(classes = "top-status-bar") {
        p {
            +"status bar"
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
