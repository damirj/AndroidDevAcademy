package Koans.Introduction

fun main() {
    val stringList = listOf<String>("A", "B", "C")
    println(joinOptions(stringList))
}

//Make JSON format specifying only 2 parameters of joinToString function
fun joinOptions(options: Collection<String>) = options.joinToString(prefix = "[", postfix = "]")