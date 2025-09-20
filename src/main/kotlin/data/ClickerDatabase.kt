package dev.kotlinssr.data

import dev.kotlinssr.data.model.PlayerAccount
import dev.kotlinssr.data.model.PlayerData

interface ClickerDatabase {
    fun doesUserExist(username: String): Boolean
    fun createAccount(username: String, password: String): AuthResult
    fun verifyPassword(username: String, password: String): AuthResult

    fun getPlayerAccount(playerId: String): PlayerAccount?
    fun updatePlayerData(playerId: String, update: (PlayerData) -> PlayerData)
}
