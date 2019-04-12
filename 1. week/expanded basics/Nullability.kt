package basics

fun main() {
/*
Kotlin tracks the difference between nullable and non-nullable types
 */

    //val name: String = null //Null can not be a value of a non-null type string

    //We need to inform compiler that we will allow null with a '?'
    var name: String? = null
    name = "Damir"
    name = null
    //println(name.length) // Can't be used 'cause it might be null


    //Smart cast
/*
We use smart cast when we want to use our nullable variable. But it can produce large amount of code
 */
    if(name != null){
        println(name.length)
    }


    //Safe null access
/*
Alternative for smart cast. Using '?'
*/
    println(name?.length)


    //Force operator
/*
It forces a nullable type into a non-nullable type. It uses '!!' operator
*/
    //println(name!!.length) //it will throw KotlinNullPointerException if the value of variable i null


    //Elvis operator
/*
When we have a nullable reference r, we can say "if r is not null, use it, otherwise use some non-null value x":
*/
    val r: String? = null
    val x: String = "I'm not null"
    val newVal: String = r ?: x

    println(newVal) // I'm not null
}