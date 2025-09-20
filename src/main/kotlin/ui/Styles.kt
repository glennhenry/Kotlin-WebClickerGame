package dev.kotlinssr.ui

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.Align
import kotlinx.css.*
import kotlinx.css.Border
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
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
import kotlinx.css.boxSizing
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.gap
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.outline
import kotlinx.css.padding
import kotlinx.css.position
import kotlinx.css.properties.Animation
import kotlinx.css.properties.Animations
import kotlinx.css.properties.FillMode
import kotlinx.css.properties.IterationCount
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.s
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateY
import kotlinx.css.px
import kotlinx.css.textAlign
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.html.InputAutoComplete.name

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
                width = 800.px
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
                height = 100.px
                position = Position.relative

                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.spaceBetween
                padding = Padding(0.px, 24.px)
            }
            rule(".stats-container") {
                display = Display.flex
                flexDirection = FlexDirection.row
                gap = 32.px
                alignItems = Align.center
            }
            rule(".stats-column-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 8.px

                backgroundColor = Color("#ffffff22") // translucent overlay
                padding = Padding(12.px, 16.px)
                borderRadius = 8.px
            }
            rule(".logout-button") {
                position = Position.absolute
                top = 2.px
                right = 2.px

                backgroundColor = Color("#e74c3c")
                color = Color.white
                border = Border.none
                borderRadius = 6.px
                padding = Padding(4.px, 8.px)
                cursor = Cursor.pointer
                fontWeight = FontWeight.bold

                hover {
                    backgroundColor = Color("#c0392b")
                }
            }

            rule(".main-content") {
                height = 100.px
                position = Position.relative
                display = Display.flex
                justifyContent = JustifyContent.center
                alignItems = Align.center
                backgroundColor = Color("#a39f99")
            }
            rule(".click-me") {
                backgroundColor = Color("#bc71c1")
                fontSize = 20.px
                border = Border(2.px, BorderStyle.solid, Color("#ca8ccc"))
                borderRadius = 8.px
                padding = Padding(8.px)
                fontFamily = "Russo One"
                cursor = Cursor.pointer
                color = Color.whiteSmoke
                hover {
                    backgroundColor = Color("#8e6491")
                }
            }
            rule(".clicked-text") {
                fontSize = 12.px
                position = Position.absolute
                opacity = 1
                animationName = "floatUp"
                animationDuration = 1.s
                animationTimingFunction = Timing.easeOut
                animationIterationCount = 1
                animationFillMode = FillMode.forwards
            }

            keyframes("floatUp") {
                from {
                    opacity = 1
                    transform { translateY(0.px) }
                }
                to {
                    opacity = 0
                    transform { translateY((-50).px) }
                }
            }

            rule(".shop-section") {
                backgroundColor = Color(Styles.shopSectionBg)
                padding = Padding(12.px)
            }
            rule("#upgrades-container") {
                display = Display.flex
                gap = 8.px
                flexDirection = FlexDirection.row
                alignItems = Align.center
                justifyContent = JustifyContent.center
            }
            rule(".upgrades-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns.repeat("2, 1fr") // 2 columns
                gap = 16.px
            }
            rule(".shop-card") {
                backgroundColor = Color("#ffffff22")
                padding = Padding(12.px)
                display = Display.flex
                flexDirection = FlexDirection.column
                border = Border(1.px, BorderStyle.solid, color = Color(Styles.siteBorder))
                borderRadius = 8.px
                width = 300.px
            }
            rule(".upgrade-title") {
                fontWeight = FontWeight.bold
                fontSize = 16.px
            }
            rule(".upgrade-description") {
                fontSize = 12.px
            }
            rule(".upgrade-cost") {
                fontSize = 12.px
                display = Display.flex
                flexDirection = FlexDirection.row
                gap = 4.px
            }
            rule(".emphasized-text") {
                color = Color("#f9d187")
            }
            rule(".buy-button") {
                padding = Padding(8.px)
                backgroundColor = Color("#5977bd")
                border = Border.none
                borderRadius = 6.px
                cursor = Cursor.pointer
                width = LinearDimension.fitContent
                hover {
                    backgroundColor = Color("#8098d1")
                }
            }
            rule(".disabled-buy-button") {
                padding = Padding(8.px)
                backgroundColor = Color("#9fa6b5")
                border = Border.none
                borderRadius = 6.px
                width = LinearDimension.fitContent
                cursor = Cursor.default
            }
            rule(".buy-text") {
                fontWeight = FontWeight.bold
                color = Color.white
            }
            rule(".load-page-button") {
                border = Border.none
                borderRadius = 6.px
                backgroundColor = Color("#f9d187")
                fontSize = 16.px
                fontWeight = FontWeight.bold
                padding = Padding(6.px)
                cursor = Cursor.pointer
                disabled {
                    cursor = Cursor.notAllowed
                }
            }
        }
    }
}

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
