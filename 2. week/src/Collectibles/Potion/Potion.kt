package Collectibles.Potion

import Collectibles.Collectible

class Potion(name: String, size: Int, buyPrice: Int, sellPrice: Int, val effect: PotionEffect): Collectible(name, size, buyPrice, sellPrice) {

}