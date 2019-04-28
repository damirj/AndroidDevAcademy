package Enemies

import Locations.LocationNames

interface Enemy {

    companion object{
         var counter: Int = 0
             get() = field++
    }

    val id: Int
    val name: String
    var location: LocationNames
    val damage: Int
    var vitality: Int

    fun attack():Int
}