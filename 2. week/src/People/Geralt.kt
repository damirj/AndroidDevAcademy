package People

import Collectibles.Armor.Armor
import Collectibles.Weapon.Weapon
import Collectibles.Weapon.WeaponType
import Enemies.Enemy
import Enemies.Monsters.Monster
import Inventory
import Locations.LocationNames
import Locations.SmallPlaces
import SchoolOfWitchers
import kotlin.random.Random

object Geralt: Witcher(name = "Geralt", age = 107, location = LocationNames.COURTYARD, vitality = 150,
    steelSword = Weapon("Witcher's steel sword", 10, 200, 40, WeaponType.STEEL_SWORD,20),
    silverSword = Weapon("Witcher's silver sword", 10, 200, 40, WeaponType.SILVER_SWORD, 20),
    school = SchoolOfWitchers.WOLF_SCHOOL
) {

    val inventory: Inventory = Inventory()
    var toxicity: Int = 0
    var endurance: Int = 100
    var alive: Boolean = true



    fun attack(enemy: Enemy, weapon: Weapon): Int{
        if (enemy !is Monster) {
            if (weapon == steelSword) return steelSword.damage else return silverSword.damage/3
        }else {
            if (weapon == silverSword) return silverSword.damage else return steelSword.damage/3
        }
    }

}