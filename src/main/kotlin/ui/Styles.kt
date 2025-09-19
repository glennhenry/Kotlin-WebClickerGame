package dev.kotlinssr.ui

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.Border
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.border
import kotlinx.css.color
import kotlinx.css.fontFamily
import kotlinx.css.height
import kotlinx.css.maxHeight
import kotlinx.css.maxWidth
import kotlinx.css.px
import kotlinx.css.width

// CSS styling and theme constants
object Styles {
    // Colors
    const val siteBorder = "#c2bfbc"
    const val topStatusBarBg = "#857b6e"
    const val shopSectionBg = "#857b6e"

    // Fonts
    const val default = "Arial, sans-serif"

    // Spacing
    val spacingSmall = 4.px
    val spacingMedium = 8.px
    val spacingLarge = 16.px
}

fun Route.stylesCss() {
    get("/styles.css") {
        call.respondCss {
            body {
                width = 600.px
                height = 400.px
                fontFamily = Styles.default
                border = Border(
                    width = 1.px, style = BorderStyle.solid, color = Color(Styles.siteBorder)
                )
            }
            rule(".top-status-bar") {
                backgroundColor = Color(Styles.topStatusBarBg)
            }
            rule(".shop-section") {
                backgroundColor = Color(Styles.shopSectionBg)
            }
        }
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
