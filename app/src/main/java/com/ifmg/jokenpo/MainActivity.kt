package com.ifmg.jokenpo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ifmg.jokenpo.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userChoice: String? = null
    private var playerScore = 0
    private var cpuScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.rockImg.setOnClickListener {
            if (userChoice == null) {
                userChoice = "rock"
                updateUserChoiceText(getString(R.string.rock))
                playRound()
            }
        }

        binding.paperImg.setOnClickListener {
            if (userChoice == null) {
                userChoice = "paper"
                updateUserChoiceText(getString(R.string.paper))
                playRound()
            }
        }

        binding.scissorsImg.setOnClickListener {
            if (userChoice == null) {
                userChoice = "scissors"
                updateUserChoiceText(getString(R.string.scissors))
                playRound()
            }
        }

        binding.newTurnBtn.setOnClickListener {
            resetRound()
        }

        binding.newTurnBtn.setOnLongClickListener {
            resetGame()
            true
        }
    }

    private fun updateUserChoiceText(choice: String) {
        binding.yourChoiceTxt.text = getString(R.string.your_choice) + choice
    }

    private fun playRound() {
        if (userChoice == null) {
            Toast.makeText(this, getString(R.string.choose_movement), Toast.LENGTH_SHORT).show()
            return
        }

        val cpuChoice = listOf("rock", "paper", "scissors").random()
        val cpuChoiceText = when (cpuChoice) {
            "rock" -> getString(R.string.rock)
            "paper" -> getString(R.string.paper)
            "scissors" -> getString(R.string.scissors)
            else -> ""
        }

        val resultText = when {
            userChoice == cpuChoice -> getString(R.string.draw)
            (userChoice == "rock" && cpuChoice == "scissors") ||
                    (userChoice == "paper" && cpuChoice == "rock") ||
                    (userChoice == "scissors" && cpuChoice == "paper") -> {
                playerScore++
                getString(R.string.you_won)
            }
            else -> {
                cpuScore++
                getString(R.string.cpu_won)
            }
        }

        binding.cpuChoiceTxt.text = getString(R.string.cpu_choice) + " $cpuChoiceText"
        binding.scoreTxt.text = "$playerScore : $cpuScore"
        Toast.makeText(this, resultText, Toast.LENGTH_SHORT).show()

        disableChoices()
    }

    private fun disableChoices() {
        binding.rockImg.isClickable = false
        binding.paperImg.isClickable = false
        binding.scissorsImg.isClickable = false
    }

    private fun enableChoices() {
        binding.rockImg.isClickable = true
        binding.paperImg.isClickable = true
        binding.scissorsImg.isClickable = true
    }

    private fun resetRound() {
        userChoice = null
        binding.yourChoiceTxt.text = getString(R.string.your_choice)
        binding.cpuChoiceTxt.text = getString(R.string.cpu_choice)

        enableChoices()
    }

    private fun resetGame() {
        playerScore = 0
        cpuScore = 0
        binding.scoreTxt.text = "$playerScore : $cpuScore"
        binding.yourChoiceTxt.text = getString(R.string.your_choice)
        binding.cpuChoiceTxt.text = getString(R.string.cpu_choice)

        enableChoices()

        Toast.makeText(this, getString(R.string.game_reset), Toast.LENGTH_SHORT).show()
    }
}
