package Locations

import Collectibles.Collectible
import Enemies.Enemy
import Person
import Quest

class SmallPlaces(val name: LocationNames, val items: MutableList<Collectible> = mutableListOf<Collectible>(), val neighbourPlaces: List<LocationNames>){

    val people = mutableListOf<Person>()
    val enemies = mutableListOf<Enemy>()

}

