package Koans.Collections

// Return all products this customer has ordered
val Customer.orderedProducts: Set<Product> get() {
    return orders.flatMap { it.products }.toSet()
}

// Return all products that were ordered by at least one customer
val Shop.allOrderedProducts: Set<Product> get() {
    return customers.flatMap { it.orderedProducts }.toSet()
}


fun main() {


    /*
    val novaLista = listOf<nest>(
        nest("Damir",22, Grad("Osijek", 100_000, listOf("Damir","Samir","Kamir"))),
        nest("Amir",21, Grad("Vinkovci", 32_000, listOf("Amir", "Smir", "Kmir"))),
        nest("Mir",23, Grad("Zagreb", 800_000, listOf("Mir", "Sir", "Kir"))),
        nest("Ir",20, Grad("Vukovar", 25_000, listOf("Ir", "Sr", "Kr"))),
        nest("R",14, Grad("Rijeka", 170_000, listOf("R", "S", "K"))),
        nest("Rim",17, Grad("Split", 190_000, listOf("Rim", "Sim", "Kim"))),
        nest("Rimad",66, Grad("Zadar", 75_000, listOf("Rimad", "Simad", "Kimad")))
        )

    for (li in novaLista)println(li)

    println(novaLista.map { it.city.population }.max())

    println(novaLista.maxBy { it.city.population }?.city?.name)

    println(novaLista.filter { it.city.population >= 100_000 }.map { it.name })

    println(novaLista.flatMap { it.city.popularPpl })

    println(novaLista.filter { it.city.population >= 100_000 }.flatMap { it.city.popularPpl })
*/

/*
    val lista = listOf(1,42,5,10,15,2)
    println(lista)

    println(lista.map { it * 2 })

    println(lista.filter { it > 10 })

    println(lista.max())

    println(lista.maxBy { it })
*/
}

//data class nest(val name: String, val age: Int, val city: Grad)

//data class Grad(val name: String, val population: Int, val popularPpl: List<String>)
