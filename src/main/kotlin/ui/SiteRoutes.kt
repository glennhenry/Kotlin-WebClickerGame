package dev.kotlinssr.ui

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import kotlinx.html.head
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.respondHtml
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.col
import kotlinx.css.h2
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.id
import kotlinx.html.onClick

@OptIn(ExperimentalKtorApi::class)
fun Route.siteRoutes() {
    var state = 12

    get("/") {
        call.respondHtml {
            head {
                websiteHead()
            }
            body {
                h1 {

                    +"Hello world"
                }
                StateCounter(state)
                button {
                    attributes.hx {
                        post = "/clicked"
                        target = "#statecounter"
                        swap = HxSwap.outerHtml
                        trigger = "click"
                    }
                    +"Click me"
                }
            }
        }
    }

    post("/clicked") {
        state += 1
        call.respondHtml {
            body {
                StateCounter(state)
            }
        }
    }
}

fun FlowContent.StateCounter(state: Int) {
    h2 {
        id = "statecounter"
        +"the state: $state"
    }
}
