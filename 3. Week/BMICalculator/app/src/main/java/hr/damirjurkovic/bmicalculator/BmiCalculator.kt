package hr.damirjurkovic.bmicalculator

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class BmiCalculator(private val etHeight: EditText, private val etWeight: EditText,
                    private val tvBMIResult: TextView , private val ivPerson: ImageView,
                    private val tvBMIType: TextView, private val tvBMIDescription: TextView) {

    private companion object {
        const val normalnweight = "You have normal weight"
        const val overweight = "Being overweight or fat is having more body fat than is optimally healthy. Being overweight is especially common where food supplies are plentiful and lifestyles are sedentary."
        const val underweight = "An underweight person is a type of person whose body weight is considered too low to be healthy. Underweight people have a body mass index (BMI) of under 18.5 or a weight 15% to 20% below that normal for their age and height group."
        const val obese = "Obesity is a medical condition in which excess body fat has accumulated to an extent that it may have a negative effect on health."
    }

    fun calculateBMI() {
        var result = etWeight.text.toString().toDouble() / (etHeight.text.toString().toDouble() * etHeight.text.toString().toDouble())
        showBmiType("%.2f".format(result).toDouble())
    }

    private fun showBmiType(bmi: Double) {
        tvBMIResult.text = bmi.toString()

        when{
            bmi < 18.5 -> {
                ivPerson.setImageResource(R.drawable.underweight)
                tvBMIType.text = "Underweight"
                tvBMIDescription.text = underweight
            }

            bmi > 18.5 && bmi < 25 -> {
                ivPerson.setImageResource(R.drawable.normalnweight)
                tvBMIType.text = "Normal weight"
                tvBMIDescription.text = normalnweight
            }

            bmi > 25 && bmi < 30 -> {
                ivPerson.setImageResource(R.drawable.overweight)
                tvBMIType.text = "Overweight"
                tvBMIDescription.text = overweight
            }

            bmi > 30 -> {
                ivPerson.setImageResource(R.drawable.obese)
                tvBMIType.text = "Obese"
                tvBMIDescription.text =  obese
            }
        }

    }

}