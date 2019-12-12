package com.bfazeli.homework3.ui.dice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bfazeli.homework3.R
import java.util.*

class PairOfDiceFragment : Fragment() {
    private lateinit var diceImage: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var rollButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pair_of_dice, container, false)

        setupViews(root)
        setupListeners()

        return root
    }

    private fun setupListeners() {
        rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun setupViews(root: View) {
        rollButton = root.findViewById(R.id.roll_button)
        diceImage = root.findViewById(R.id.dice_image)
        diceImage2 = root.findViewById(R.id.dice_image2)
    }

    private fun rollDice() {
        diceImage.setImageResource(generateRandomDice())
        diceImage2.setImageResource(generateRandomDice())
    }

    private fun generateRandomDice(): Int {
        return when (Random().nextInt(6) + 1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}