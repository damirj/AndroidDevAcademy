import Collectibles.Armor.Armor
import Collectibles.Collectible
import Collectibles.Potion.Potion
import Collectibles.Weapon.Weapon

class Inventory {

    val size = 50;
    private val inventory = mutableListOf<Collectible>()

    fun addItem(item: Collectible): Boolean = if (inventory.sumBy { it.size } + item.size > 50) false else inventory.add(item)


    fun throwOut(item: Collectible){
        if (inventory.find {it == item} != null){
            inventory.remove(item)
        }
    }

    fun fetch(itemName: String): Collectible = inventory.first { it.itemName == itemName }

    fun checkIfHave(itemName: String): Boolean = inventory.any { it.itemName == itemName }

    fun leftSpaceInInventory(): Int = size - inventory.sumBy { it.size }

    fun isEmpty(): Boolean = inventory.isEmpty()

    fun checkIfPotion(item: Collectible): Boolean = item is Potion

    fun checkIfWeapon(item: Collectible): Boolean = item is Weapon

    fun checkIfArmor(item: Collectible): Boolean = item is Armor

    fun checkIfHasPotions(): Boolean = inventory.any { it is Potion }

    fun getAllEquipts(): List<Collectible> = inventory.filter { it is Weapon || it is Armor }

    fun getAllPotions(): List<Potion> = inventory.filter { it is Potion } as List<Potion>


}