import Collectibles.Collectible
import Enemies.Enemy

class Quest(val name: String, val description: String, val killEnemy: MutableList<Enemy> = mutableListOf<Enemy>(), val findItem: MutableList<Collectible> = mutableListOf<Collectible>()) {

    fun isItDone(): Boolean = killEnemy.isEmpty() && findItem.isEmpty()

}