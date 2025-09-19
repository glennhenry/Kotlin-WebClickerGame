package dev.kotlinssr.ui

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.color
import kotlinx.css.fontFamily
import kotlinx.css.px

// CSS styling and theme constants
object Styles {
    // Colors
    const val textColor = "#635a4e"

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
                fontFamily = Styles.default
            }
            rule(".home-page-title") {
                color = Color(Styles.textColor)
            }
        }
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
