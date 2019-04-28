package People

import Collectibles.Armor.Armor
import Collectibles.Weapon.Weapon
import Collectibles.Weapon.WeaponType
import Locations.LocationNames
import Locations.SmallPlaces
import Person
import SchoolOfWitchers

open class Witcher(name:String, age:Int, location: LocationNames, vitality: Int,
                   var steelSword: Weapon = Weapon("Witcher's steel sword", 10, 200, 40, WeaponType.STEEL_SWORD,30),
                   var silverSword: Weapon = Weapon("Witcher's silver sword", 10, 200, 40, WeaponType.SILVER_SWORD, 20),
                   var armor: Armor = Armor("No Armor", 0, 0, 0, 0, 0),
                   var school: SchoolOfWitchers): Person(name, age, location, vitality) {



}