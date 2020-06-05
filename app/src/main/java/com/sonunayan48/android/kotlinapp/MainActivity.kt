package com.sonunayan48.android.kotlinapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var steps: Int = 0
    private val len = 3
    private lateinit var arr: Array<IntArray>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arr = Array(len) { IntArray(len) }
        box00.setOnClickListener { performStep(it, 0, 0) }
        box01.setOnClickListener { performStep(it, 0, 1) }
        box02.setOnClickListener { performStep(it, 0, 2) }
        box10.setOnClickListener { performStep(it, 1, 0) }
        box11.setOnClickListener { performStep(it, 1, 1) }
        box12.setOnClickListener { performStep(it, 1, 2) }
        box20.setOnClickListener { performStep(it, 2, 0) }
        box21.setOnClickListener { performStep(it, 2, 1) }
        box22.setOnClickListener { performStep(it, 2, 2) }
        resetBtn.setOnClickListener { recreate() }
    }

    private fun performStep(v: View, row: Int, col: Int) {
        val view: TextView = v as TextView
        if ((view.text.toString()).isEmpty()) {
            when (steps % 2) {
                0 -> {
                    view.text = "O"
                    arr[row][col] = 1
                }
                else -> {
                    view.text = "X"
                    arr[row][col] = 2
                }
            }
            if (checkWin()) {
                createGameOverDialog(0, view.text.toString())
                return
            }
            steps++
        }
        if (steps == 9) {
            createGameOverDialog(1)
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0 until len) {
            if (arr[i][0] == arr[i][1]
                && arr[i][0] == arr[i][2]
                && arr[i][0] != 0
            ) {
                return true
            }
        }
        for (i in 0 until len) {
            if (arr[0][i] == arr[1][i]
                && arr[0][i] == arr[2][i]
                && arr[0][i] != 0
            ) {
                return true
            }
        }
        if (arr[0][0] == arr[1][1]
            && arr[0][0] == arr[2][2]
            && arr[0][0] != 0
        ) {
            return true
        }
        if (arr[0][2] == arr[1][1]
            && arr[0][2] == arr[2][0]
            && arr[0][2] != 0
        ) {
            return true
        }
        return false
    }

    private fun createGameOverDialog(i: Int, a: String = "") {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(
            when (i) {
                0 -> "Winner!"
                else -> "Game Over"
            }
        )
        builder.setMessage(
            when (i) {
                0 -> "Player $a is the Winner, Congrats!!!"
                else -> "The game has been ended and no one won the game."
            }
        )
        builder.setPositiveButton(
            "Restart"
        ) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
            recreate()
        }
        builder.setNegativeButton(
            "Exit Game"
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            finishAffinity()
        }
        builder.setCancelable(false)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
