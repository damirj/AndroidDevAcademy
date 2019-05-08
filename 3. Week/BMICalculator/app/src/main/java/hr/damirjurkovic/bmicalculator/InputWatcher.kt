package hr.damirjurkovic.bmicalculator

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class InputWatcher(private val etHeight: EditText, private val etWeight: EditText, private val btnCalculate: Button): TextWatcher {

    init {
        etHeight.addTextChangedListener(this)
        etWeight.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        isInputCorrect(etHeight.text.toString(), etWeight.text.toString())
    }

    private fun isInputCorrect(height: String, weight: String) {
        btnCalculate.isEnabled = height.isNotEmpty() && weight.isNotEmpty()
        if (btnCalculate.isEnabled){
            btnCalculate.isEnabled = (weight.toDouble() > 0 && weight.toDouble() < 350) && (height.toDouble() > 0 && height.toDouble() < 2.5)
        }
    }
}