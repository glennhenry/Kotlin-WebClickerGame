package dev.kotlinssr.data

interface ClickerDatabase {
    fun doesUserExist(username: String): Boolean
    fun createAccount(username: String, password: String): AuthResult
    fun verifyPassword(username: String, password: String): AuthResult
}
