package Enemies.Monsters

import Collectibles.Extracts.MonsterExtract
import Enemies.Enemy
import Locations.LocationNames

class Monster(override val name: String, val specie: Species, override var location: LocationNames,override var vitality: Int, override val damage: Int,
                  monsterExtract: MonsterExtract): Enemy {

    override val id = Enemy.counter

    override fun attack(): Int {
        return damage
    }

}