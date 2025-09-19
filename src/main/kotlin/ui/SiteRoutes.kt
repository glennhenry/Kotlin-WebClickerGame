package dev.kotlinssr.ui

import dev.kotlinssr.ui.pages.HomePage
import dev.kotlinssr.ui.pages.PlayPage
import dev.kotlinssr.validateSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p

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

    get("/play") {
        if (validateSession()) {
            call.respondHtml {
                head {
                    websiteHead()
                }
                body {
                    PlayPage()
                }
            }
        } else {
            call.respondHtml(status = HttpStatusCode.Unauthorized) {
                body {
                    p {
                        +"Session invalid"
                    }
                }
            }
        }
    }
}
