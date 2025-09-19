package dev.kotlinssr.ui.pages

import io.ktor.htmx.HxSwap
import io.ktor.htmx.html.hx
import io.ktor.utils.io.ExperimentalKtorApi
import kotlinx.css.input
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.i
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.onClick
import kotlinx.html.p

@OptIn(ExperimentalKtorApi::class)
fun FlowContent.HomePage() {
    div(classes = "homepage-container") {
        div(classes = "login-title-container") {
            i(classes = "fa-solid fa-mouse-pointer login-title-icon")
            div {
                h2(classes = "login-title") {
                    +"Kotlin clicker game"
                }
                p {
                    +"Login/register below"
                }
            }
        }
        form(classes = "login-form-container", method = FormMethod.post) {
            attributes.hx {
                post = "/login"
                target = "#login-result"
                swap = HxSwap.innerHtml
            }

            div {
                p(classes = "text-field-title") {
                    +"Username"
                }
                input(classes = "text-field", type = InputType.text, name = "Username") {
                    id = "username-field"
                }
            }
            div {
                p(classes = "text-field-title") {
                    +"Password"
                }
                input(classes = "text-field", type = InputType.password, name = "Password") {
                    id = "password-field"
                }
            }
            button(type = ButtonType.submit) {
                // validate username and password in client-side
                // returns true if valid, and HTMX will continue request
                onClick = "return validateForm(event)"
                +"Login/register"
            }
        }
        div {
            id = "login-result"
        }
    }
}
