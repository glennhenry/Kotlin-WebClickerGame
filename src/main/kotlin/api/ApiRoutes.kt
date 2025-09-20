package dev.kotlinssr.api

import dev.kotlinssr.context.ServerContext
import dev.kotlinssr.data.model.allUpgrades
import dev.kotlinssr.getPlayerIdFromSession
import dev.kotlinssr.ui.pages.ShopCard
import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.p

@OptIn(ExperimentalKtorApi::class)
fun Route.apiRoutes(serverContext: ServerContext) {
    get("/buy") {

    }

    get("/shop") {
        val page = call.parameters["page"]?.toIntOrNull() ?: 1
        val pageSize = 4
        val start = (page - 1) * pageSize
        val upgrades = allUpgrades.drop(start).take(pageSize)

        val playerId = getPlayerIdFromSession()
        val data = playerId?.let { serverContext.db.getPlayerAccount(it) }?.playerData
        if (playerId == null || data == null) {
            call.respondHtml {
                body {
                    p {
                        +"You are not logged in"
                        a {
                            href = "/"
                            +"Back"
                        }
                    }
                }
            }
            return@get
        }

        call.respondHtml {
            body {
                button(classes = "load-page-button") {
                    disabled = page <= 1
                    attributes["hx-get"] = "/shop?page=${page - 1}"
                    attributes["hx-target"] = "#upgrades-container"
                    attributes["hx-swap"] = "innerHTML"
                    +"<<"
                }
                div(classes = "upgrades-grid") {
                    upgrades.forEach {
                        ShopCard(it, canBuy = data.clickPoints < it.cost, bought = data.upgrades.contains(it))
                    }
                }
                button(classes = "load-page-button") {
                    disabled = page > allUpgrades.size / pageSize
                    attributes["hx-get"] = "/shop?page=${page + 1}"
                    attributes["hx-target"] = "#upgrades-container"
                    attributes["hx-swap"] = "innerHTML"
                    +">>"
                }
            }
        }
    }
}
