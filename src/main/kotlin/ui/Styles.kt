package dev.kotlinssr.ui

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.Align
import kotlinx.css.Border
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.GridColumn
import kotlinx.css.GridTemplateColumns
import kotlinx.css.GridTemplateRows
import kotlinx.css.JustifyContent
import kotlinx.css.Margin
import kotlinx.css.Outline
import kotlinx.css.Padding
import kotlinx.css.Position
import kotlinx.css.TextAlign
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.border
import kotlinx.css.borderColor
import kotlinx.css.borderRadius
import kotlinx.css.boxShadow
import kotlinx.css.boxSizing
import kotlinx.css.color
import kotlinx.css.columnGap
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.gap
import kotlinx.css.gridColumn
import kotlinx.css.gridTemplateColumns
import kotlinx.css.gridTemplateRows
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.maxHeight
import kotlinx.css.maxWidth
import kotlinx.css.outline
import kotlinx.css.padding
import kotlinx.css.position
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.BoxShadows
import kotlinx.css.px
import kotlinx.css.rowGap
import kotlinx.css.textAlign
import kotlinx.css.width

// CSS styling and theme constants
object Styles {
    // Colors
    const val siteBorder = "#c2bfbc"
    const val topStatusBarBg = "#857b6e"
    const val shopSectionBg = "#857b6e"

    const val successText = "#6b8a66"
    const val failedText = "#a15450"

    // Fonts
    const val default = "Arial, sans-serif"
}

fun Route.stylesCss() {
    get("/styles.css") {
        call.respondCss {
            /* Global styles */
            rule("*") {
                margin = Margin(0.px)
                padding = Padding(0.px)
                boxSizing = BoxSizing.borderBox
            }
            body {
                width = 600.px
                height = 400.px
                margin = Margin(4.px)
                fontFamily = Styles.default
                border = Border(
                    width = 1.px, style = BorderStyle.solid, color = Color(Styles.siteBorder)
                )
            }

            /* Home page login */
            rule(".homepage-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 36.px
                alignItems = Align.center
                justifyContent = JustifyContent.center
            }
            rule(".login-title-container") {
                padding = Padding(all = 4.px)
                textAlign = TextAlign.center
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                gap = 16.px
            }
            rule(".login-title") {
                fontSize = 24.px
            }
            rule(".login-title-icon") {
                fontSize = 28.px
            }
            rule(".login-form-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 16.px
                border = Border(
                    width = 1.px, style = BorderStyle.solid, color = Color(Styles.siteBorder)
                )
                padding = Padding(16.px)
            }
            rule(".text-field-title") {
                fontSize = 14.px
            }
            rule(".text-field") {
                outline = Outline.none
                padding = Padding(4.px)
                border = Border(1.px, BorderStyle.solid, Color.gray)
                borderRadius = 4.px
            }
            rule(".text-field:focus") {
                borderColor = Color.darkGray
            }
            rule(".success-text") {
                color = Color(Styles.successText)
            }
            rule(".failed-text") {
                color = Color(Styles.failedText)
            }

            /* Play page */
            rule(".top-status-bar") {
                backgroundColor = Color(Styles.topStatusBarBg)
                height = 150.px
                position = Position.relative
            }
            rule(".stats-container") {
                display = Display.flex
                flexDirection = FlexDirection.row
                gap = 24.px

            }
            rule(".stats-column-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 8.px
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
