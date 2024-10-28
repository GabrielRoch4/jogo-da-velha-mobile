package com.example.jogodavelha

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var resetButton: Button
    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        gridLayout = findViewById(R.id.gridLayout)
        resetButton = findViewById(R.id.resetButton)

        setupButtons()
        resetButton.setOnClickListener { resetGame() }
    }

    private fun setupButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener { onCellClicked(button, i) }
        }
    }

    private fun onCellClicked(button: Button, index: Int) {
        val row = index / 3
        val col = index % 3

        if (board[row][col].isEmpty()) {
            board[row][col] = currentPlayer
            button.text = currentPlayer
            button.setTextColor(if (currentPlayer == "X") Color.RED else Color.BLUE)
            checkForWinner()
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "Jogador $currentPlayer, é a sua vez!"
        }
    }

    private fun checkForWinner() {
        for (i in 0..2) {
            // Verifica linhas e colunas
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
                announceWinner(board[i][0])
                return
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i].isNotEmpty()) {
                announceWinner(board[0][i])
                return
            }
        }

        // Verifica diagonais
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
            announceWinner(board[0][0])
            return
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
            announceWinner(board[0][2])
            return
        }

        // Verifica empate
        if (board.all { row -> row.all { it.isNotEmpty() } }) {
            statusTextView.text = "Empate!"
        }
    }

    private fun announceWinner(winner: String) {
        statusTextView.text = "Jogador $winner venceu!"
        disableButtons()
    }

    private fun disableButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
        }
        currentPlayer = "X"
        statusTextView.text = "Jogador X, é a sua vez!"
    }
}

