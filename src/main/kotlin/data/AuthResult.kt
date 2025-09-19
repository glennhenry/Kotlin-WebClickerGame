package dev.kotlinssr.data

sealed class AuthResult(message: String) {
    object AccountDoesntExist: AuthResult("Account does not exist")
    object UsernameAlreadyExist: AuthResult("Username already exists")
    object WrongPassword: AuthResult("Password is wrong")
    object Success: AuthResult("Login/register succeed")
}
