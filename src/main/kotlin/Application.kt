package dev.kotlinssr

import dev.kotlinssr.ui.siteRoutes
import dev.kotlinssr.ui.stylesCss
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.html.body
import kotlinx.html.p
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import java.io.File

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
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
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
}

@Serializable
data class Session(
    val playerId: String,
    val sessionId: String,
)
