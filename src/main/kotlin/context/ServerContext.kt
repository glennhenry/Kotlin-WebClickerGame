package dev.kotlinssr.context

import dev.kotlinssr.data.ClickerDatabase

data class ServerContext(
    val db: ClickerDatabase,
)
