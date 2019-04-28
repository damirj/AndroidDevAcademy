package Collectibles.Armor

import Collectibles.Collectible

class Armor(itemName: String, size: Int, buyPrice: Int, sellPrice: Int, val bonusVitality: Int, val blockDamage: Int): Collectible(itemName, size, buyPrice, sellPrice) {
}