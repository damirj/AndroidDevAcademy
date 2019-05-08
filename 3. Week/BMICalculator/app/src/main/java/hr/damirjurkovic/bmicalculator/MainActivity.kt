package hr.damirjurkovic.bmicalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
    }

    private fun setUpUI() {
        val inputWatcher = InputWatcher(etHeight, etWeight, btnCalculate)
        val bmiCalculator = BmiCalculator(etHeight, etWeight, tvBMIResult, ivPerson, tvBMIType, tvBMIDescription)
        btnCalculate.setOnClickListener { bmiCalculator.calculateBMI() }
    }

}
