package dev.kotlinssr.ui

import dev.kotlinssr.ui.pages.HomePage
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.html.body
import kotlinx.html.head

@OptIn(ExperimentalKtorApi::class)
fun Route.siteRoutes() {
    get("/") {
        call.respondHtml {
            head {
                websiteHead()
            }
            body {
                HomePage()
            }
        }
    }
}
