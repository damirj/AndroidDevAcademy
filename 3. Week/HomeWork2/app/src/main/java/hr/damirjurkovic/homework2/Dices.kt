package hr.damirjurkovic.homework2

import android.content.Context
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class Dices (private val context: Context, private val diceList: List<ImageView>, private val btnRollDice: Button){
    private val toggleFixedList = mutableListOf<Boolean>(false, false, false, false, false, false)
    private var rollCount = 0

    init {
        diceList.forEachIndexed { index, imageView -> imageView.setOnClickListener { toggle(index) }  }
    }

    private fun toggle(index: Int) {
        toggleFixedList[index] = !toggleFixedList[index]
        if (toggleFixedList[index]) Toast.makeText(context, "Dice ${index + 1} is locked", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Dice ${index + 1} is unlocked", Toast.LENGTH_SHORT).show()
    }

    fun roll() {
        rollCount++
        if (rollCount >= 2) {
            btnRollDice.isEnabled = false
            diceList.forEach { it.isEnabled = false }
        }
        for ((index, die) in diceList.withIndex()){
            when(Random.nextInt(1,7)){
                1 -> setDiceImage(die, R.drawable.dice1, index)
                2 -> setDiceImage(die, R.drawable.dice2, index)
                3 -> setDiceImage(die, R.drawable.dice3, index)
                4 -> setDiceImage(die, R.drawable.dice4, index)
                5 -> setDiceImage(die, R.drawable.dice5, index)
                6 -> setDiceImage(die, R.drawable.dice6, index)
            }
        }
    }

    private fun setDiceImage(die: ImageView, imageResourceId: Int, index: Int){
        if (rollCount == 2 && !toggleFixedList[index]) die.setImageResource(imageResourceId)
        else if (rollCount == 1) die.setImageResource(imageResourceId)
    }

}