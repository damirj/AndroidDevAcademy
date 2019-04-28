package Collectibles.Weapon

import Collectibles.Collectible

class Weapon(itemName: String, size: Int, buyPrice: Int, sellPrice: Int, val type: WeaponType, var damage: Int): Collectible(itemName, size, buyPrice, sellPrice) {

}