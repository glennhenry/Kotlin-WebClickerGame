package dev.kotlinssr

import dev.kotlinssr.ui.siteRoutes
import dev.kotlinssr.ui.stylesCss
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    routing {
        // Route for site (serve webpage with respondHtml)
        // Separate it from API routes
        siteRoutes()
        stylesCss()
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
