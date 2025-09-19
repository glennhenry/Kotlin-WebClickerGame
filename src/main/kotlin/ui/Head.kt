package dev.kotlinssr.ui

import kotlinx.html.HEAD
import kotlinx.html.link
import kotlinx.html.script

fun HEAD.websiteHead() {
    script { src = "https://unpkg.com/htmx.org@2.0.6" }
    script { src = "/scripts/validate.js" }
    link {
        rel = "stylesheet"
        href = "/styles.css"
        type = "text/css"
    }
    link {
        rel = "preconnect"
        href = "https://fonts.googleapis.com"
    }
    link {
        rel = "stylesheet"
        href =
            "https://fonts.googleapis.com/css2?family=Russo+One&family=Source+Code+Pro:wght@200..900&display=swap"
    }
    link {
        rel = "stylesheet"
        href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
        attributes["crossorigin"] = "anonymous"
        attributes["referrerpolicy"] = "no-referrer"
    }
}
