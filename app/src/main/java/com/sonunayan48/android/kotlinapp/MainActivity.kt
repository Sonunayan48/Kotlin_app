package com.sonunayan48.android.kotlinapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This is the main gaming activity where both the users can play the game
 */
class MainActivity : AppCompatActivity() {
    //creating an integer variable that would hold the total number of moves in the game as there could be
    //a maximum of nine moves
    private var moves: Int = 0

    //a constant integer variable that holds the length of array (discussed below)
    private val len = 3

    //creating an array that would contain integer array as its elements or simply a 2D array
    //This integer array hold the moves of both the players
    private lateinit var arr: Array<IntArray>

    //creating constant string keys to get the player names from the starting intent
    private val playerO: String = "playerO"
    private val playerX: String = "playerX"

    //variable to hold player names
    private var oName: String? = null
    private var xName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting the starting intent using this.intent method
        val openingIntent: Intent = this.intent
        //getting the player names from the starting intent using the Intent class getStringExtra() function
        oName = openingIntent.getStringExtra(playerO)
        xName = openingIntent.getStringExtra(playerX)
        //initializing the above declared 2D array
        arr = Array(len) { IntArray(len) }

        //setting the onClickListener to the the gridview elements to call the performStep() function with
        //the respective parameters
        box00.setOnClickListener { performStep(it, 0, 0) }
        box01.setOnClickListener { performStep(it, 0, 1) }
        box02.setOnClickListener { performStep(it, 0, 2) }
        box10.setOnClickListener { performStep(it, 1, 0) }
        box11.setOnClickListener { performStep(it, 1, 1) }
        box12.setOnClickListener { performStep(it, 1, 2) }
        box20.setOnClickListener { performStep(it, 2, 0) }
        box21.setOnClickListener { performStep(it, 2, 1) }
        box22.setOnClickListener { performStep(it, 2, 2) }
        //setting onClickListener() to the reset board button that would simply recreate the activity that
        //would makes the game board and array empty
        resetBtn.setOnClickListener { recreate() }
        //showing the message to the player to make the first move
        message.text = getString(R.string.first_message, oName)
    }

    /**
     * This function helps in performing the respective moves based on the player click. It takes three
     * parameters, First the view on which the player has clicked, second, the row on which the user clicked
     * , and last, the column on which the user clicked as its just like a 3x3 matrix. The views are helpful
     * in performing moves (O or X) based on the condition specified below and the rows and columns are used
     * in the 2D array initialized above to store those moves as integers (1 for O and 2 for X)
     */
    private fun performStep(v: View, row: Int, col: Int) {
        //getting the textview from the view parameter
        val view: TextView = v as TextView
        //only performing the move on the tapped view if view doesn't have any moves performed on it
        if ((view.text.toString()).isEmpty()) {
            //checking whether the number of moves is even or odd using the kotlin when keyword
            when (moves % 2) {
                //if the number of moves are even then performing the O move
                0 -> {
                    //setting the text as O to the tapped view
                    view.text = "O"
                    //setting the current element to 1 in the 2D array
                    arr[row][col] = 1
                    //setting message to for the next player to perform another move
                    message.text = getString(R.string.comm_mess, xName)
                }
                //if the number of moves are odd then performing the X move
                else -> {
                    //setting the text as X to the tapped view
                    view.text = "X"
                    //setting the current element to 2 in the 2D array
                    arr[row][col] = 2
                    //setting message to for the next player to perform another move
                    message.text = getString(R.string.comm_mess, oName)
                }
            }
            //checking winning condition after performing the move using the checkWin() function
            if (checkWin()) {
                //if the winning condition is achieved then the showing the winning dialog using the
                //createGameOverDialog() function created below
                message.visibility = View.INVISIBLE
                createGameOverDialog(
                    0,
                    //getting the winner name by getting the current move and using the when keyword
                    when (view.text.toString()) {
                        "X" -> xName
                        else -> oName
                    }!!
                )
                return
            }
            //incrementing the number of moves
            moves++
        }
        //checking if the game has to be end as if the number of moves have been reached to 9
        if (moves == 9) {
            createGameOverDialog(1)
        }
    }

    /**
     * This function basically checks whether the winning condition has reached or not. If the condition has
     * been reached then it returns true, else it returns false
     */
    private fun checkWin(): Boolean {
        //checking if the winning condition has been reached along any row
        for (i in 0 until len) {
            if (arr[i][0] == arr[i][1]
                && arr[i][0] == arr[i][2]
                && arr[i][0] != 0
            ) {
                return true
            }
        }
        //checking if the winning condition has been reached along any column
        for (i in 0 until len) {
            if (arr[0][i] == arr[1][i]
                && arr[0][i] == arr[2][i]
                && arr[0][i] != 0
            ) {
                return true
            }
        }
        //checking if the winning condition has been reached along any diagonal
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
        //if winning condition hasn't reached then finally returning false
        return false
    }

    /**
     * This function basically creates the gameover dialog based of the winning condition or exceeding
     * moves that is determined the passed integer parameter i (0 for winning dialog and 1 for exceeding moves
     * dialog. If the winning dialog is reached then it also takes the winner player name.
     */
    private fun createGameOverDialog(i: Int, a: String = "") {
        //using the AlertDialog.Builder class to build the required alert dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //setting the title and message of the dialog based on the type of dialog
        builder.setTitle(
            when (i) {
                0 -> "Winner!"
                else -> "Game Over"
            }
        )
        builder.setMessage(
            when (i) {
                0 -> "$a, You Won, Congrats!!!"
                else -> "The game has been ended and no one won the game."
            }
        )
        //setting the positive button to reset the game board to play again
        builder.setPositiveButton(
            "Play Again"
        ) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
            recreate()
        }
        //setting a neutral button to change the player names by sending them back to the welcome activity
        builder.setNeutralButton(
            "Change Players"
        ) { _: DialogInterface?, _: Int ->
            onBackPressed()
        }
        //setting the negative button to exit the game
        builder.setNegativeButton(
            "Exit Game"
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            finishAffinity()
        }
        builder.setCancelable(false)
        //getting the final alert dialog by the help of AlertDialog.builder class create() method
        val dialog: AlertDialog = builder.create()
        //displaying the final alert dialog
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
}
