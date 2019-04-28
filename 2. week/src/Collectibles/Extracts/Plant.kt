package Collectibles.Extracts

import Collectibles.Collectible

class Plant(itemName: String, size: Int, buyPrice: Int, sellPrice: Int,
            override val basicSubstance: BasicSubstance,
            override val additionalSubstance: AdditionalSubstance): Collectible(itemName, size, buyPrice, sellPrice), AlchemIngredient{

}