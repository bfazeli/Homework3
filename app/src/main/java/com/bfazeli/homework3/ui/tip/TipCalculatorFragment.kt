package com.bfazeli.homework3.ui.tip

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bfazeli.homework3.R
import com.bfazeli.homework3.databinding.FragmentTipCalculatorBinding

import com.bfazeli.homework3.util.dismissKeyBoard
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.view.*

class TipCalculatorFragment : Fragment() {
    private lateinit var binding: FragmentTipCalculatorBinding
    private var floatingActionButton: FloatingActionButton? = null
    private lateinit var result: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupBindings(inflater, container)
        setupListeners()
        activity?.invalidateOptionsMenu()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tip_calculator, container, false)
        floatingActionButton = container?.rootView?.fab
        floatingActionButton?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingActionButton?.hide()
    }

    private fun setupListeners() {
        binding.calculateTipButton.setOnClickListener {
            activity?.dismissKeyBoard()

            if (binding.inputSub.text.toString().trim().isNotEmpty()
                && (binding.fifteenPercentCheckBox.isChecked
                        || binding.eighteenPercentCheckBox.isChecked
                        || binding.twentyPercentCheckBox.isChecked)) {
                result = "Subtotal: $${binding.inputSub.text}\n"
                binding.totalFor15 = ""
                binding.tipFor15 = ""
                binding.totalFor18 = ""
                binding.tipFor18 = ""
                binding.totalFor20 = ""
                binding.tipFor20 = ""
                calculateTip()
                binding.inputSub.text.clear()
            }
            else {
                Toast.makeText(activity?.applicationContext,
                    "Enter a value and select at least one CheckBox",
                    Toast.LENGTH_SHORT).show()
            }
        }
        binding.parentView.setOnClickListener {
            activity?.dismissKeyBoard()
        }
        floatingActionButton?.setOnClickListener {
            if (binding.output15TotalTextView.text.isNotEmpty()
                || binding.output18TotalTextView.text.isNotEmpty()
                || binding.output20TotalTextView.text.isNotEmpty()) {

                startActivity(getShareIntent())
            }
            else {
                Toast.makeText(activity?.applicationContext,
                    "Enter a value and select at least one CheckBox",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun calculateTip() {
        val subTotal = binding.inputSub.text.toString().toDouble()

        if (binding.fifteenPercentCheckBox.isChecked) {
            binding.tipFor15 =
                "%.2f".format( subTotal * .15)
            binding.totalFor15 =
                "%.2f".format(subTotal + binding.tipFor15.toString().toDouble())

            result += "Total amount with 15% tip: $${binding.totalFor15}\nTip amount for 15%: $${binding.tipFor15}\n"
        }
        if (binding.eighteenPercentCheckBox.isChecked) {
            binding.tipFor18 =
                "%.2f".format(subTotal * .18)
            binding.totalFor18 =
                "%.2f".format(subTotal + binding.tipFor18.toString().toDouble())

            result += "Total amount with 18% tip: $${binding.totalFor18}\nTip amount for 18%: $${binding.tipFor18}\n"
        }
        if (binding.twentyPercentCheckBox.isChecked) {
            binding.tipFor20 =
                "%.2f".format(subTotal * .20)
            binding.totalFor20 =
                "%.2f".format(subTotal + binding.tipFor20.toString().toDouble())

            result += "Total amount with 20% tip: $${binding.totalFor20}\nTip amount for 20%: $${binding.tipFor20}"
        }
    }

    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/email")
            .putExtra(Intent.EXTRA_TEXT, result)
        return shareIntent
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (binding.output15TotalTextView.text.isNotEmpty()
            || binding.output18TotalTextView.text.isNotEmpty()
            || binding.output20TotalTextView.text.isNotEmpty()) {
            when(item.itemId) {
                R.id.action_settings -> startActivity(getShareIntent())
            }
        }

        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val email: MenuItem = menu.findItem(R.id.action_settings)
        email.isVisible = true

        super.onPrepareOptionsMenu(menu)
    }
}