import Chapters.Chapter
import Collectibles.Armor.Armor
import Collectibles.Extracts.Plant
import Collectibles.Potion.Potion
import Collectibles.Potion.PotionEffect
import Collectibles.Weapon.Weapon
import Collectibles.Weapon.WeaponType
import Enemies.Boss.Boss
import Enemies.Enemy
import Enemies.NonMonsters.LowLife
import Enemies.NonMonsters.Salamandra
import Locations.LocationNames
import Locations.MainArea
import Locations.SmallPlaces
import People.Geralt
import People.Mage
import People.Witcher
import java.lang.NumberFormatException

fun main() {
    //POKUSATI PRECI IGRU


    //PROLOGUE CHAPTER INITIALIZATION

    val smallPlaces = mapOf<LocationNames, SmallPlaces>(
        LocationNames.COURTYARD to SmallPlaces(LocationNames.COURTYARD, neighbourPlaces = listOf(LocationNames.DINING_HALL, LocationNames.UPPER_COURYARD)),
        LocationNames.DINING_HALL to SmallPlaces(LocationNames.DINING_HALL, neighbourPlaces = listOf(LocationNames.COURTYARD, LocationNames.KITCHEN, LocationNames.WITCHERS_LABORATORY, LocationNames.MAIN_CORRIDOR)),
        LocationNames.MAIN_CORRIDOR to SmallPlaces(LocationNames.MAIN_CORRIDOR, neighbourPlaces = listOf(LocationNames.ARMORY, LocationNames.LIBRARY, LocationNames.DINING_HALL)),
        LocationNames.ARMORY to SmallPlaces(LocationNames.ARMORY, mutableListOf(Armor("Studded leather jacket", 10, 2500, 500, 50, 5)), listOf(LocationNames.MAIN_CORRIDOR)),
        LocationNames.KITCHEN to SmallPlaces(LocationNames.KITCHEN, neighbourPlaces = listOf(LocationNames.DINING_HALL)),
        LocationNames.LIBRARY to SmallPlaces(LocationNames.LIBRARY, mutableListOf(Weapon("Gwalhir Steel Sword", 5, 1500, 600, WeaponType.STEEL_SWORD, 40)), listOf(LocationNames.MAIN_CORRIDOR)),
        LocationNames.WITCHERS_LABORATORY to SmallPlaces(LocationNames.WITCHERS_LABORATORY, mutableListOf(Potion("Swallow", 1, 100, 1, PotionEffect.HEAL)), listOf(LocationNames.DINING_HALL)),
        LocationNames.UPPER_COURYARD to SmallPlaces(LocationNames.UPPER_COURYARD, neighbourPlaces = listOf(LocationNames.COURTYARD))
    )

    val prologueMainAreas = listOf<MainArea>(MainArea("Kaer Morhen", smallPlaces))

    val prologueEnemyList = mutableListOf<Enemy>(
        Salamandra("Salamandra Theif", 30, LocationNames.COURTYARD, 100, 5),
        Salamandra("Salamandra Theif", 30, LocationNames.ARMORY, 80, 5),
        Salamandra("Salamandra Theif", 30, LocationNames.WITCHERS_LABORATORY, 100, 10),
        Salamandra("Salamandra Theif", 30, LocationNames.KITCHEN, 100, 3),
        Salamandra("Salamandra Theif", 30, LocationNames.LIBRARY, 100, 3),
        Boss("Frightener", LocationNames.UPPER_COURYARD, 30, 200)
    )

    val prologuePeopleList = mutableListOf<Person>(
        Witcher("Vesemir", 176, LocationNames.COURTYARD, 200, school = SchoolOfWitchers.WOLF_SCHOOL),
        Witcher("Eskel", 107, LocationNames.LIBRARY, 100, school = SchoolOfWitchers.WOLF_SCHOOL),
        Mage("Triss", 32, LocationNames.COURTYARD, 100)
    )

    val prologueQuest = mutableListOf<Quest>(
        Quest("Kill Salamandra", "You have to kill all Salamandra in Kaer Mohren", prologueEnemyList.filterIndexed { index, enemy -> index < 5 }.toMutableList()),
        Quest("Kill Boss Frightener", "You have to kill boss Frightener (Upper courtyard)", mutableListOf(prologueEnemyList[5]))
        )

    val Prologue = Chapter("Prologue", prologueMainAreas, prologueQuest, prologuePeopleList, prologueEnemyList)

    //END OF PROLOGUE INITIALIZATION


    var interaction: String
    var numberOfInteraction: Int
    println("""
        |               Play the Witcher?
        |   Venture through a dark world where is no good or evil,
        |       just choices and their consequences?
        |
        |                    Y/N""".trimMargin())
    var play: String? = readLine()
    if(play == "Y" || play == "y") {
        println("Let's begin\n\n")
        println("You are at Kaer Morhen, practicing in Courtyard, until Salamandra bandits broke in...\n")
        println("Vesemir: Geralt, we need to get rid of the bandits. Quick grab your sword and kill them...\n")
        while (Geralt.alive){
            Prologue.availableInteractions()
            interaction = readLine() ?: ""
            if (Prologue.chosenInteraction(interaction)) {
                try {
                    numberOfInteraction = readLine()?.toInt() ?: 0
                } catch (e: NumberFormatException){
                    println("\nYou need to enter the number!\n Try again\n")
                    numberOfInteraction = 0
                }
                Prologue.chosenInteractionFinal(interaction, numberOfInteraction)
                if (Prologue.isChapterDone()) {
                    println("Chapter: ${Prologue.name} is done!\n And this is the only chapter i made, so this is The End, well played...")
                    Geralt.alive = false
                }
            }

        }
    }else if(play == "N" || play == "n"){
        println("Farewell...")
    }

}