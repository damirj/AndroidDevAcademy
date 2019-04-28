package Collectibles.Extracts

import Collectibles.Collectible

open class MonsterExtract(itemName: String, size: Int, buyPrice: Int, sellPrice: Int,
                          override val basicSubstance: BasicSubstance,
                          override val additionalSubstance: AdditionalSubstance): Collectible(itemName, size, buyPrice, sellPrice), AlchemIngredient {
}