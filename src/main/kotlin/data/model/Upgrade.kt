package dev.kotlinssr.data.model

data class Upgrade(
    val name: String,
    val description: String,
    val cost: Long,
    val clickPointIncrease: Long,
)

fun List<Upgrade>.calculateIncrease(): Long {
    return 1 + this.sumOf { it.clickPointIncrease }
}

val exampleUpgrades = listOf(
    Upgrade(
        name = "Rally", description = "The first upgrade in the game",
        cost = 10, clickPointIncrease = 1
    ),
    Upgrade(
        name = "Sweat", description = "Sweaty clicker",
        cost = 25, clickPointIncrease = 3
    ),
    Upgrade(
        name = "Minister", description = "The minister upgrade.",
        cost = 50, clickPointIncrease = 5
    ),
    Upgrade(
        name = "Funny", description = "The funniest clicker XD",
        cost = 100, clickPointIncrease = 10
    ),
    Upgrade(
        name = "Army", description = "Army of click ready to help",
        cost = 200, clickPointIncrease = 25
    ),
    Upgrade(
        name = "Coat", description = "Clickers C(G)OAT",
        cost = 500, clickPointIncrease = 50
    ),
    Upgrade(
        name = "Maid", description = "Cute clicker",
        cost = 2000, clickPointIncrease = 100
    ),
    Upgrade(
        name = "Matter", description = "Clicks matter.",
        cost = 3000, clickPointIncrease = 150
    ),
    Upgrade(
        name = "Pastel", description = "Pastel clicker color",
        cost = 5000, clickPointIncrease = 200
    ),
    Upgrade(
        name = "Blade", description = "PHP template engine",
        cost = 10000, clickPointIncrease = 250
    ),
    Upgrade(
        name = "God", description = "The last upgrade.",
        cost = 1_000_000_000, clickPointIncrease = 1_000_000_000
    )
)
