package dev.kotlinssr

import dev.kotlinssr.context.ServerContext
import dev.kotlinssr.data.ClickerDatabaseImpl
import dev.kotlinssr.ui.siteRoutes
import dev.kotlinssr.ui.stylesCss
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.body
import kotlinx.html.p
import kotlinx.serialization.Serializable
import java.io.File

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val serverContext = ServerContext(db = ClickerDatabaseImpl())

    routing {
        // Route for site (serve webpage with respondHtml)
        // Separate it from API routes
        siteRoutes()
        stylesCss()
        staticFiles("scripts", File("scripts"))

        post("/login") {
            val params = call.receiveParameters()
            val username = params["username"]
            val password = params["password"]

            if (username.isNullOrBlank() || password.isNullOrBlank()) {
                call.respondHtml(status = HttpStatusCode.BadRequest) {
                    body {
                        p {
                            +"Username or password is empty"
                        }
                    }
                }
            }

            val usernameExists = false
            if (usernameExists) {
                // login
                val wrongPassword = false
                if (wrongPassword) {
                    // status must be OK or else HTMX won't swap
                    call.respondHtml(status = HttpStatusCode.OK) {
                        body {
                            p(classes = "failed-text") {
                                +"Password is wrong; if registering, choose another name"
                            }
                        }
                    }
                } else {
                    call.response.headers.append("HX-Redirect", "/play")
                }
            } else {
                // register
                call.response.headers.append("HX-Redirect", "/play")
            }
        }
    }

    install(CallLogging)
    install(Sessions) {
        cookie<Session>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}

@Serializable
data class Session(
    val playerId: String,
    val sessionId: String,
)
