package dev.kotlinssr.data.model

import java.util.UUID

data class PlayerAccount(
    val playerId: String,
    val username: String,
    val password: String,
    val playerData: PlayerData
) {
    companion object {
        fun dummy(): PlayerAccount {
            return PlayerAccount(
                playerId = UUID.randomUUID().toString(),
                username = randomUsername("player", 6),
                password = randomString(12),
                playerData = PlayerData.dummy()
            )
        }

        fun generateDummyAccounts(amount: Int): List<PlayerAccount> {
            return List(amount) { dummy() }
        }
    }
}

fun randomUsername(prefix: String, length: Int): String {
    return "$prefix-${randomString(length)}"
}

fun randomString(length: Int): String {
    val pool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val str = StringBuilder(length)
    while (str.length < length) {
        str.append(pool.random())
    }
    return str.toString()
}
