package Enemies.NonMonsters

import Enemies.Enemy
import Locations.LocationNames
import Locations.SmallPlaces
import Person

class LowLife(name: String, age: Int, location: LocationNames, vitality: Int, override val damage: Int): Person(name, age, location, vitality), Enemy {


    override val id = Enemy.counter

    override fun attack(): Int {
        return damage
    }


}