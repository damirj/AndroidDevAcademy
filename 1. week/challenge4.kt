const val AGE_REQUIREMENT = 7

fun main() {

    val data = mapOf(
        "users1.csv" to listOf(32, 45, 17, -1, 34),
        "users2.csv" to listOf(19, -1, 67, 22),
        "users3.csv" to listOf(),
        "users4.csv" to listOf(56, 32, 18, 44)
    )

    println(data)

    val average = data.flatMap { it.value }.filter { it > AGE_REQUIREMENT }.average()
    println(average)

    val namesWithFaultyData = data.filterValues { it.any { it <= AGE_REQUIREMENT } }.keys
    println(namesWithFaultyData)

    val countFaultyData = data.flatMap { it.value }.count { it <= AGE_REQUIREMENT }
    println(countFaultyData)
}