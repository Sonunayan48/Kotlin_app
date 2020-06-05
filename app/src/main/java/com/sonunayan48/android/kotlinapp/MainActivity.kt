package com.sonunayan48.android.kotlinapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var steps: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                0 -> view.text = "O"
                else -> view.text = "X"
            }
            steps++
        }
        if (steps == 9) {
            createGameOverDialog()
        }
    }

    private fun createGameOverDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over!")
        builder.setMessage("The game has been ended and no one won the game.")
        builder.setPositiveButton(
            "Restart",
            DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
                recreate()
            })
        builder.setNegativeButton(
            "Exit Game",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                dialog.cancel()
                finishAffinity()
            })
        builder.setCancelable(false)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
