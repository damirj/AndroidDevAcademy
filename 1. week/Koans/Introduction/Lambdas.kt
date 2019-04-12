package Koans.Introduction

fun main() {

    val intCollection = (1..100).toList()

    if (containsEven(intCollection)) println("My collection contains even number") else println("My collection doesn't contain even number")

}

fun containsEven(collection: Collection<Int>): Boolean = collection.any { it % 2 == 0 }