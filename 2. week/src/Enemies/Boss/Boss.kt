package Enemies.Boss

import Enemies.Enemy
import Locations.LocationNames

class Boss(override val name: String, override var location: LocationNames, override val damage: Int, override var vitality: Int): Enemy {

    override val id = Enemy.counter

    override fun attack(): Int {
        return damage
    }
}