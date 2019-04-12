package basics

fun main() {
    val price = 25

    //if _ else
    if (price > 50) {
        println("On sale.")
    } else {
        println("Not on sale.")
    }

    //We can get the same thing with this:
    if (price > 50) println("On sale.") else println("Not on sale.") //Preferred way


    //when without argument
    when {
        price == 0 -> println("On sale")
        price > 10 && price < 25 -> println("Regular price")
        price > 26 && price < 50 -> println("Pricey")
        else -> println("Very expensive")
    }


    //When(value)
    when (price) {
        0 -> println("Zerooo")
        in 1..25 -> println("That's hella cheap")
        in 26..50 -> println("Don't even look at it")
        else -> println("in another life")
    }



/*
An expression is a statment that evaluates to a value. In kotlin 'if _ else' and 'when' are expressions.
That means that we can a result from an expression store directly to a variable
*/
    //In this example we store value 'true' directly in the variable newVal
    val newVal = if (2 < 4) true else false
    println(newVal)


    //value 'ok!' will be stored in variable name
    val expressons = if ("kotlin" == "java") "ok?" else "ok!"
    println(expressons)


/*
Example of using 'when' to store result.
In this case 'when' must be exhaustive (write out every scenario, if that's not possible compiler will force us to write 'else' block).
*/

    //Since the price is 25, in variable 'buying' value of "buy for sure" will be stored
    var buying = when (price) {
        0 -> {
            println("Zerooo")
            "buy"
        }
        in 1..25 -> {
            println("That's hella cheap")
            "buy for sure"
        }
        in 26..50 -> {
            println("Don't even look at it")
            "don't buy"
        }
        else -> {
            println("in another life")
            "run"
        }
    }
    println(buying)

    /*
    also in 'when' you can put any function that returns a value that can be compared
     */

    val newPrice = 120

    buying = when(newPrice) {
        0 -> {
            println("Zerooo")
            "buy"
        }
        10+30 -> {
            println("That's hella cheap")
            "buy for sure"
        }
        5*4*3*2*1 -> {
            println("Don't even look at it")
            "don't buy"
        }
        else -> {
            println("in another life")
            "run"
        }
    }
    println(buying)
}

