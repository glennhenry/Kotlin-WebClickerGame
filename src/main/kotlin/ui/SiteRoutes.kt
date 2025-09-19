package dev.kotlinssr.ui

import io.ktor.htmx.*
import io.ktor.htmx.html.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.html.*

@OptIn(ExperimentalKtorApi::class)
fun Route.siteRoutes() {
    var state = 12

    get("/") {
        call.respondHtml {
            head {
                websiteHead()
            }
            body {
                h1(classes = "home-page-title") {
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
