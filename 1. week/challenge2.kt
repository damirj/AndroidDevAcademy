import kotlin.random.Random

fun main() {
    val myInts = List(100) {Random.nextInt(2, 100)}

    for (my in myInts){
        if (my <= 10) break

        println(my)
    }
}