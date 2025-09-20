package dev.kotlinssr.api

import dev.kotlinssr.UserSession
import dev.kotlinssr.context.ServerContext
import dev.kotlinssr.data.model.allUpgrades
import dev.kotlinssr.getPlayerIdFromSession
import dev.kotlinssr.ui.pages.ShopCard
import dev.kotlinssr.ui.pages.TopStatusBar
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

@OptIn(ExperimentalKtorApi::class)
fun Route.apiRoutes(serverContext: ServerContext) {
    post("/click") {
        val playerId = getPlayerIdFromSession()
        val playerData = playerId?.let { serverContext.db.getPlayerAccount(it) }?.playerData
        if (playerId == null || playerData == null) {
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
            return@post
        }

        serverContext.db.updatePlayerData(playerId) {
            it.copy(clickPoints = it.clickPoints + it.pointPerClick, totalClicks = it.totalClicks + 1)
        }

        val updatedData = serverContext.db.getPlayerAccount(playerId)!!.playerData

        call.respondHtml {
            body {
                // update status bar
                div {
                    id = "top-status-id"
                    TopStatusBar(updatedData)
                }
            }
        }
    }

    get("/buy") {
        val playerId = getPlayerIdFromSession()!!
        val upgradeName = call.parameters["name"] ?: return@get
        val upgradeToBuy = allUpgrades.find { it.name == upgradeName}!!

        serverContext.db.updatePlayerData(playerId) {
            it.copy(upgrades = it.upgrades + upgradeToBuy, pointPerClick = upgradeToBuy.clickPointIncrease)
        }
    }

    get("/shop") {
        val page = call.parameters["page"]?.toIntOrNull() ?: 1
        val pageSize = 4
        val start = (page - 1) * pageSize
        val upgrades = allUpgrades.drop(start).take(pageSize)

        val playerId = getPlayerIdFromSession()
        val playerData = playerId?.let { serverContext.db.getPlayerAccount(it) }?.playerData
        if (playerId == null || playerData == null) {
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
                        ShopCard(
                            it,
                            canBuy = playerData.clickPoints > it.cost,
                            bought = playerData.upgrades.contains(it)
                        )
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
