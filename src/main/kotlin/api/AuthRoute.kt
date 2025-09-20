package dev.kotlinssr.api

import dev.kotlinssr.UserSession
import dev.kotlinssr.context.ServerContext
import dev.kotlinssr.data.AuthResult
import dev.kotlinssr.setSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import kotlinx.html.body
import kotlinx.html.p
import kotlin.text.isNullOrBlank

fun Route.authRoute(serverContext: ServerContext) {
    post("/login") {
        val params = call.receiveParameters()
        val username = params["username"]
        val password = params["password"]

        println("/login request: username=$username, password=${if (password.isNullOrBlank()) "<empty>" else password}")

        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            println("Missing username or password")
            call.respondHtml(status = HttpStatusCode.BadRequest) {
                body {
                    p { +"Username and password mustn't be empty" }
                }
            }
            return@post
        }

        val usernameExists = serverContext.db.doesUserExist(username)
        println("doesUserExist($username) -> $usernameExists")

        if (usernameExists) {
            // login
            val result = serverContext.db.verifyPassword(username, password)
            println("verifyPassword($username) -> $result")

            when (result) {
                AuthResult.AccountDoesntExist -> {
                    println("Account does not exist (unexpected, since usernameExists was true)")
                    call.respondHtml(status = HttpStatusCode.OK) {
                        body {
                            p(classes = "failed-text") {
                                +"Account don't exist [${result.message}]"
                            }
                        }
                    }
                }

                AuthResult.WrongPassword -> {
                    println("Wrong password for $username")
                    call.respondHtml(status = HttpStatusCode.OK) {
                        body {
                            p(classes = "failed-text") {
                                +"Password is wrong; if registering, choose another name [${result.message}]"
                            }
                        }
                    }
                }

                AuthResult.UsernameAlreadyExist -> {
                    println("Unexpected AuthResult: UsernameAlreadyExist after verifying password [${result.message}]")
                    call.respond(HttpStatusCode.InternalServerError, "HTTP 500")
                }

                is AuthResult.Success -> {
                    println("Login success for $username, redirecting to /play")
                    setSession(playerId = result.playerId)
                    call.response.headers.append("HX-Redirect", "/play")
                }
            }
        } else {
            // register
            println("Registering new account for $username")
            val result = serverContext.db.createAccount(username, password) as AuthResult.Success // for simplicity in error handling
            setSession(playerId = result.playerId)
            call.response.headers.append("HX-Redirect", "/play")
        }
    }

    get("/logout") {
        call.sessions.clear<UserSession>()
        call.response.headers.append("HX-Redirect", "/")
    }
}
