package dev.kotlinssr.data

sealed class AuthResult(val message: String) {
    object AccountDoesntExist: AuthResult("Account does not exist")
    object UsernameAlreadyExist: AuthResult("Username already exists")
    object WrongPassword: AuthResult("Password is wrong")
    class Success(val playerId: String): AuthResult("Login/register succeed")
}