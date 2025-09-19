package dev.kotlinssr.data.model

import kotlin.random.Random

/**
 * Player data
 *
 * @property clickPoints the player's global amount of click units
 * @property totalClicks the amount of click player has done
 * @property pointPerClick the amount of click unit each click generates
 * @property upgrades list of upgrades bought by the player
 */
data class PlayerData(
    val clickPoints: Long,
    val totalClicks: Long,
    val pointPerClick: Long,
    val upgrades: List<Upgrade>
) {
    companion object {
        fun dummy(): PlayerData {
            val upgrades = exampleUpgrades.shuffled().take(Random.nextInt(1, exampleUpgrades.size))

            return PlayerData(
                clickPoints = Random.nextLong(1, 100_000),
                totalClicks = Random.nextLong(1, 100),
                pointPerClick = upgrades.calculateIncrease(),
                upgrades = upgrades
            )
        }
    }
}
