package hr.damirjurkovic.homework2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
    }

    private fun setUpUI() {
        val diceList = listOf<ImageView>(ivDice1, ivDice2, ivDice3, ivDice4, ivDice5, ivDice6)
        val rollDice = Dices(this, diceList, btnRollDice)

        btnRollDice.setOnClickListener { rollDice.roll() }
    }

}
