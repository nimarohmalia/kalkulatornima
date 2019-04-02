package kalkulatornima.rohmalia.nima.kalkulatornima

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.txtScreen = findViewById<TextView>(R.id.txtScreen)
        setNumericOnClickListener()
        setOperatorOnClickListener()
    }

    private val numericButtons = intArrayOf(
            R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour,
            R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine
    )

    private val operatorButtons = intArrayOf(R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide)

    private var txtScreen: TextView? = null

    private var lastNumeric: Boolean = false

    private var stateError: Boolean = false

    private var lastDot: Boolean = false


    private fun setNumericOnClickListener() {
        val listener = View.OnClickListener { v ->
            val button = v as Button
            if (stateError) {
                txtScreen!!.text = button.text
                stateError = false
            } else {
                txtScreen!!.append(button.text)
            }
            lastNumeric = true
        }
        for (id in numericButtons) {
            findViewById<View>(id).setOnClickListener(listener)
        }
    }

    /**
     * OPERATOR BUTTON
     */
    private fun setOperatorOnClickListener() {
        val listener = View.OnClickListener { v ->
            if (lastNumeric && !stateError) {
                val button = v as Button
                txtScreen!!.append(button.text)
                lastNumeric = false
                lastDot = false
            }
        }
        for (id in operatorButtons) {
            findViewById<View>(id).setOnClickListener(listener)
        }
        findViewById<TextView>(R.id.btnDot).setOnClickListener(View.OnClickListener {
            if (lastNumeric && !stateError && !lastDot) {
                txtScreen!!.append(".")
                lastNumeric = false
                lastDot = true
            }
        })

        findViewById<TextView>(R.id.btnClear).setOnClickListener(View.OnClickListener {
            txtScreen!!.text = ""
            lastNumeric = false
            stateError = false
            lastDot = false
        })

        findViewById<TextView>(R.id.btnEqual).setOnClickListener(View.OnClickListener { onEqual() })
    }


    /**
     * CALCULATION
     */
    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = txtScreen!!.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                txtScreen!!.text = java.lang.Double.toString(result)
                lastDot = true
            } catch (ex: ArithmeticException) {
                txtScreen!!.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }


}
