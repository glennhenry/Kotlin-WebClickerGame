package dev.kotlinssr

import dev.kotlinssr.context.ServerContext
import dev.kotlinssr.data.ClickerDatabaseImpl
import dev.kotlinssr.data.model.randomString
import dev.kotlinssr.ui.siteRoutes
import dev.kotlinssr.ui.stylesCss
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
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
        authRoute(serverContext)
    }

    install(CallLogging)
    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}

fun RoutingContext.setSession() {
    call.sessions.set(UserSession(id = randomString(12), count = 0))
}

fun RoutingContext.validateSession(): Boolean {
    return call.sessions.get<UserSession>() != null
}

@Serializable
data class UserSession(val id: String, val count: Int)
