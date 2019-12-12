package com.bfazeli.homework3.ui.home

import com.bfazeli.homework3.util.dismissKeyBoard
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bfazeli.homework3.R

class HomeFragment : Fragment() {
    private lateinit var lbEditText: EditText
    private lateinit var ubEditText: EditText
    private lateinit var withoutReplacementCheckBox: CheckBox
    private lateinit var outputTextView: TextView
    private lateinit var getNumberButton: Button
    private lateinit var contentView: ConstraintLayout
    lateinit var tipButton: Button

    private var lastLbInput: Int = -1
    private var lastUbInput: Int = -1
    private var output: Int = -1

    private val numbersWithoutReplacement = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        setupBindings(root)
        setupOnClickEvents()
        setHasOptionsMenu(false)

        return root
    }

    private fun setupBindings(root: View) {


        lbEditText = root.findViewById(R.id.lbEditText)
        ubEditText = root.findViewById(R.id.ubEditText)
        withoutReplacementCheckBox = root.findViewById(R.id.withoutReplacementCheckBox)
        outputTextView = root.findViewById(R.id.outputTextView)
        getNumberButton = root.findViewById(R.id.getNumberButton)
        tipButton = root.findViewById(R.id.tip_button)
        contentView = root.findViewById(R.id.home)
    }

    private fun setupOnClickEvents() {
        tipButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_tip)
        }

        getNumberButton.setOnClickListener {
            generateOutput(withoutReplacementCheckBox.isChecked)
        }

        contentView.setOnClickListener {
            activity?.dismissKeyBoard()
        }

        withoutReplacementCheckBox.setOnCheckedChangeListener { _, _ ->
            reset()
        }
    }

    private fun reset() {
        lbEditText.text.clear()
        ubEditText.text.clear()

        outputTextView.text = getString(R.string.reset)
    }

    private fun generateOutput(isChecked: Boolean) {
        val lbValue = lbEditText.text.toString().toInt()
        val ubValue = ubEditText.text.toString().toInt()

        if (isChecked) {
            if (lastLbInput != lbValue ||
                lastUbInput != ubValue) {
                numbersWithoutReplacement.clear()
            } else if (numbersWithoutReplacement.count() == (ubValue - lbValue + 1)) {
                Toast.makeText(activity?.applicationContext, getString(R.string.exhausted), Toast.LENGTH_SHORT).show()
                return
            }

            output = -1
            do {
                output = (lbValue..ubValue).random()
            } while (numbersWithoutReplacement.contains(output))

            outputTextView.text = output.toString()
            numbersWithoutReplacement.add(output)

            lastLbInput = lbValue
            lastUbInput = ubValue
        } else {
            outputTextView.text = (lbValue..ubValue).random().toString()
        }
    }

}