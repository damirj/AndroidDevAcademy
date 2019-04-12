package basics

fun main() {

////Vals and vars

    //var is mutable variable and can be initialized later
    var name: String
    name = "you"
    name = "me"

    var name2: String = "kotlin"
    name = name2

    println(name2) //me

    //val is used to declare read-only variable. Must be initialized when created.
    val first: String = "Some string"
    //first = "string"  //val cannot be reassigned

/*
It doesnt mean that the instance itself is immutable. It can change it's member variables via functions or properties,
but the variable itself cannot change its's value or be reassigned to another value
Example of that is mutable List, if you create it as val, then you can't make another mutable list and reassign it to the first one,
but you still can change the values inside of the List and change the lenght of the List. On the other hand if you make it var,
then you can do both.
 */

////Variable types

/*
In Kotlin, everything is an object in the sense that we can call member functions and properties on any variable.
Some of the types can have a special internal representation - for example, numbers, characters and booleans can
be represented as primitive values at runtime - but to the user they look like ordinary classes.

 Whenever possible, the Kotlin compiler will map basic types back to JVM primitives for
performance reasons. However, sometimes the values must be boxed, such as when the
type is nullable, or when it is used in generics. Boxing is the conversion from a primitive
type to a wrapper type that types place whenever an object is required but a primitive is
presented.
*/
    //Numbers                       //Width
    var longValue: Long = 100_000_000    //64
    var intValue: Int = 500_000          //32
    var shortValue: Short = 500          //16
    var byteValue: Byte = 64             //8
    var doubleValue: Double = 987.123    //64
    var floatValue: Float = 66.6f        //32

/*
Kotlin does not support automatic widening of numbers, so conversion must be invoked
explicitly. Each number has a function that will convert the value to one of the other
number types.
*/
    intValue = longValue.toInt()
    doubleValue = floatValue.toDouble()
    byteValue = doubleValue.toByte()


    //Booleans
/*
Booleans support negation, conjunction, and disjunction.
Conjunction and disjunction are lazily evaluated, so if the left-hand side satisfies
the clause, then the right-hand side will not be evaluated:
*/

    val boolValue: Boolean = 1 > 2 && 3 >2


    //Chars
/*
Not treated as a number. They represent single character. They use single quotes
*/
    val charValue: Char = 'A'


    //String
/*
Strings are immutable.
*/
    val stringValue = "int value is: $intValue"
}