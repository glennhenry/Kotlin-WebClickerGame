package dev.kotlinssr.data

import dev.kotlinssr.data.model.PlayerAccount
import dev.kotlinssr.data.model.PlayerData
import java.security.MessageDigest
import java.util.*

class ClickerDatabaseImpl : ClickerDatabase {
    val accounts = PlayerAccount.generateDummyAccounts(55).toMutableSet()

    override fun createAccount(username: String, password: String): AuthResult {
        val account = accounts.find { it.username == username }
        if (account != null) {
            return AuthResult.UsernameAlreadyExist
        }

        val newAccount = PlayerAccount(
            playerId = UUID.randomUUID().toString(),
            username = username,
            password = hashPassword(password),
            playerData = PlayerData.newgame()
        )
        accounts.add(newAccount)
        return AuthResult.Success
    }

    private fun hashPassword(pw: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(pw.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    override fun verifyPassword(username: String, password: String): AuthResult {
        val account = accounts.find { it.username == username }
        if (account == null) {
            return AuthResult.AccountDoesntExist
        }

        return if (hashPassword(password) == account.password) {
            AuthResult.Success
        } else {
            AuthResult.WrongPassword
        }
    }
}
