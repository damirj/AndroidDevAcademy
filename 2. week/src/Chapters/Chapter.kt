package Chapters

import Collectibles.Armor.Armor
import Collectibles.Collectible
import Collectibles.Potion.Potion
import Collectibles.Potion.PotionEffect
import Collectibles.Weapon.Weapon
import Collectibles.Weapon.WeaponType
import Enemies.Enemy
import Locations.LocationNames
import Locations.MainArea
import People.Geralt
import Quest
import Person

class Chapter(val name: String, val mainAreas: List<MainArea>, val quests: MutableList<Quest>, val chapterPeople: MutableList<Person>, val chapterEnemies: MutableList<Enemy>) {

    init {
        for (ppl in chapterPeople) mainAreas.flatMap { it.smallPlaces.values }.first { it.name == ppl.location }.people.add(ppl)

        for (ppl in chapterEnemies) mainAreas.flatMap { it.smallPlaces.values }.first { it.name == ppl.location }.enemies.add(ppl)

        mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location}.people.add(Geralt)
    }


    fun moveGeralt(newLocation: LocationNames){
        mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location }.people.remove(Geralt)
        mainAreas.flatMap { it.smallPlaces.values }.first { it.name == newLocation }.people.add(Geralt)
        Geralt.location = newLocation
    }


    fun fight(enemy: Enemy, weapon: Weapon): Boolean{
        while (Geralt.vitality > 0 && enemy.vitality > 0){
            enemy.vitality -= Geralt.attack(enemy, weapon)
            Geralt.vitality -= if (enemy.damage < Geralt.armor.blockDamage) 0 else enemy.damage - Geralt.armor.blockDamage
        }
        if (enemy.vitality <= 0) {
            mainAreas.flatMap { it.smallPlaces.values }.first { it.name == enemy.location }.enemies.remove(enemy)
            enemy.location = LocationNames.DEAD
            if (quests.flatMap { it.killEnemy }.any { it.id == enemy.id }){
                quests.forEach { it.killEnemy.removeAll { it.id == enemy.id } }
            }

            if (quests.any { it.isItDone() }) quests.removeIf { it.isItDone() }
        }
        if (Geralt.vitality <= 0) return false
        return true
    }



    fun collectItem(item: Collectible): Boolean{
        if (Geralt.inventory.addItem(item)) {
            mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location }.items.remove(item)
            if (quests.flatMap { it.findItem }.any {it.itemName == item.itemName}){
                quests.forEach { it.findItem.removeAll { it.itemName == item.itemName } }
            }

            if (quests.any { it.isItDone() }) {
                quests.removeIf { it.isItDone() }
            }
            return true
        }
        return false
    }


    fun consumePotion(item: Potion): Boolean{
        when (item.effect){
            PotionEffect.HEAL -> {
                Geralt.vitality += PotionEffect.HEAL.value
                Geralt.toxicity += 20
            }
            PotionEffect.INCREASE_ATTACK_DAMAGE -> {
                Geralt.steelSword.damage += PotionEffect.INCREASE_ATTACK_DAMAGE.value
                Geralt.silverSword.damage += PotionEffect.INCREASE_ATTACK_DAMAGE.value
                Geralt.toxicity += 20
            }
            PotionEffect.REDUCE_TOXICITY -> {
                if (Geralt.toxicity <= 50) Geralt.toxicity = 0 else Geralt.toxicity -= PotionEffect.REDUCE_TOXICITY.value
            }
        }
        Geralt.inventory.throwOut(item)

        //if (Geralt.toxicity >= 100) return false else return true
        return Geralt.toxicity < 100
    }


    fun equipt(equipment: Collectible?): Boolean{
        return when {
            equipment is Armor -> {
                if (Geralt.armor.itemName == ""){
                    Geralt.armor = equipment
                    Geralt.vitality += equipment.bonusVitality
                }else{
                    var tempArmor = Geralt.armor
                    Geralt.armor = equipment
                    Geralt.vitality += equipment.bonusVitality
                    Geralt.inventory.throwOut(equipment)
                    Geralt.inventory.addItem(tempArmor)
                }
                true
            }
            equipment is Weapon -> {
                var tempSword: Weapon
                if (equipment.type == WeaponType.STEEL_SWORD) {
                    tempSword = Geralt.steelSword
                    Geralt.steelSword = equipment
                    Geralt.inventory.throwOut(equipment)
                    Geralt.inventory.addItem(tempSword)
                }else if (equipment.type == WeaponType.SILVER_SWORD) {
                    tempSword = Geralt.silverSword
                    Geralt.silverSword = equipment
                    Geralt.inventory.throwOut(equipment)
                    Geralt.inventory.addItem(tempSword)
                }
                true
            }
            else -> false
        }
    }


    fun isChapterDone(): Boolean = quests.isEmpty()


    fun availableInteractions(){
        val smallplace = mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location}
        if ( smallplace.enemies.isNotEmpty() ){
            println("FIGHT")
        }
        if ( smallplace.items.isNotEmpty() ){
            println("COLLECT")
        }
        if ( Geralt.inventory.getAllEquipts().isNotEmpty() ){
            println("EQUIPT")
        }
        if ( Geralt.inventory.checkIfHasPotions()){
            println("DRINK POTION")
        }
        println("QUESTS")
        println("CHAR STATISTICS")
        println("MOVE\n")
    }

    fun chosenInteraction(interaction: String): Boolean{
        val smallplace = mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location}
        var counter = 1
        if (interaction == "FIGHT" || interaction == "COLLECT" || interaction == "EQUIPT" || interaction == "DRINK POTION" || interaction == "QUESTS" || interaction == "MOVE" || interaction == "CHAR STATISTICS"){
            println("\nChose number! (Chose 0 for back)\n")
        }
        return when{
            interaction == "FIGHT" -> {
                smallplace.enemies.forEach { println("${counter++}. ${it.name}, Vitality: ${it.vitality}, Damage: ${it.damage}") }
                true
            }

            interaction == "COLLECT" -> {
                smallplace.items.forEach { println("${counter++}. ${it.itemName}") }
                true
            }

            interaction == "EQUIPT" -> {
                Geralt.inventory.getAllEquipts().forEach { println("${counter++}. ${it.itemName}") }
                true
            }

            interaction == "DRINK POTION" -> {
                Geralt.inventory.getAllPotions().forEach { println("${counter++}. ${it.itemName}, Effect: ${it.effect}") }
                true
            }

            interaction == "QUESTS" -> {
                quests.forEach { println("-${it.description}\n") }
                true
            }

            interaction == "CHAR STATISTICS" -> {
                println("Vitality: ${Geralt.vitality}")
                println("Current location: ${Geralt.location}")
                println("Steel Sword: ${Geralt.steelSword.itemName}, damage: ${Geralt.steelSword.damage}")
                println("Silver Sword: ${Geralt.silverSword.itemName}, damage: ${Geralt.silverSword.damage}")
                println("Armor: ${Geralt.armor.itemName}, bonus vitality: ${Geralt.armor.bonusVitality}, block damage: ${Geralt.armor.blockDamage}")
                println("Toxicity: ${Geralt.toxicity}")
                println("Inventory space left: ${Geralt.inventory.leftSpaceInInventory()}")
                println("Witcher school: ${Geralt.school}")
                println("Age: ${Geralt.age}")
                true
            }

            interaction == "MOVE" -> {
                smallplace.neighbourPlaces.forEach { println("${counter++}. ${it.name}") }
                true
            }
            else -> {
                println("\nI dont understand your instructions, try again!\n")
                false
            }
        }
        println("")
    }


    fun chosenInteractionFinal(interaction: String, number: Int) {
        println("")
        if (number > 0) {
            val smallplace = mainAreas.flatMap { it.smallPlaces.values }.first { it.name == Geralt.location }
            when {
                interaction == "FIGHT" -> {
                    if (number-1 <= smallplace.enemies.size) {
                        println("\nChoose your weapon: STEEL or SILVER? (STEEL is good against humans, it's the deafult weapon.)\n")
                        val sword = readLine()
                        if (sword == "SILVER") {
                            if (fight(smallplace.enemies[number - 1], Geralt.silverSword)) {
                                println("\nEnemy killed")
                                println("Your vitality: ${Geralt.vitality}")
                            } else {
                                println("\nYou have DIED in a fight")
                                Geralt.alive = false
                            }


                        } else {
                            if (fight(smallplace.enemies[number - 1], Geralt.steelSword)) {
                                println("\nEnemy killed")
                                println("Your vitality: ${Geralt.vitality}")
                            } else {
                                println("You have DIED in a fight")
                                Geralt.alive = false
                            }
                        }
                    }
                }

                interaction == "COLLECT" -> {
                    if (number-1 <= smallplace.items.size){
                        if (collectItem(smallplace.items[number - 1])) println("Item collected") else println("You have no space in inventory")
                    }
                }

                interaction == "EQUIPT" -> {
                    if (number-1 <= Geralt.inventory.getAllEquipts().size){
                        equipt(Geralt.inventory.getAllEquipts()[number - 1])
                        println("Item Equpited")
                    }

                }

                interaction == "DRINK POTION" -> {
                    if (number-1 <= Geralt.inventory.getAllPotions().size){
                        if (consumePotion(Geralt.inventory.getAllPotions()[number - 1])){
                            println("Potion consumed")
                            println("Toxicity: ${Geralt.toxicity}")
                        } else {
                            println("You have DIED from Toxicity")
                            Geralt.alive = false
                        }
                    }
                }

                interaction == "MOVE" -> {
                    if (number-1 <= smallplace.neighbourPlaces.size){
                        moveGeralt(smallplace.neighbourPlaces[number - 1])
                    }
                }
            }
            println("")
        }
    }


}

