package basics

fun main() {

    // for loop
/*
Kotlin for loop is used to iterate over any object that defines a function or extension
function with the name 'iterator'.
 */
    for (element in 1..10) print(element)

    for (character in "Kotlin is amazing") print("$character ")

    val languages = listOf("Kotlin", "Swift", "Dart")

    for (language in languages) println("$language is great!")

    for (i in 10 downTo 0) print("$i ")

    for (i in 10 downTo 0 step 2) print("$i ")


    //while loop
    var i = 0
    var j = 10
    while ( i < j ) {
        println("$i , $j")
        i++
        j--
    }

}