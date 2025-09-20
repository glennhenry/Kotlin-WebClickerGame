package dev.kotlinssr.data

import dev.kotlinssr.data.model.PlayerAccount

interface ClickerDatabase {
    fun doesUserExist(username: String): Boolean
    fun createAccount(username: String, password: String): AuthResult
    fun verifyPassword(username: String, password: String): AuthResult

    fun getPlayerAccount(playerId: String): PlayerAccount?
}
