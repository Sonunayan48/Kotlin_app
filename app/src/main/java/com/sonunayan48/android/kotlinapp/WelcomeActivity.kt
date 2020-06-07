package com.sonunayan48.android.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * This activity is the starting activity for the game where the players enter their names based on their
 * selection O or X
 */
class WelcomeActivity : AppCompatActivity() {
    //creating two string constants that would be the keys to send the player names to the MainActivity
    private val playerO: String = "playerO"
    private val playerX: String = "playerX"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        //Loading the starting animation of the icon in the welcome screen where the icon moves up
        icon.animation = AnimationUtils.loadAnimation(this, R.anim.moving_up)
        /* Initially all the fields and buttons in the welcome screen are invisible and remain invisible
           until the icon animation finishes

           Using a new handler to make all the edittext fields, textviews and buttons VISIBLE after the
           icon animation finishes using the Handler class postDelayed() function.
         */
        val handler: Handler = Handler()
        handler.postDelayed(
            {
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE
                playBtn.visibility = View.VISIBLE
            },
            //specifying the delay time as 2 seconds
            2000
        )
        //setting onClickListener to the play now button to start the game
        playBtn.setOnClickListener {
            /*first checking whether both players has entered their name or not.
              Only starting the game when the both players has entered their names
             */
            if (player_O.text.toString().isNotEmpty()
                && player_X.text.toString().isNotEmpty()
            ) {
                //creating an intent to start the games activity
                val intent: Intent = Intent(this, MainActivity::class.java)

                //putting the names of both the players in the created intent using Intent class
                // putExtra() function
                intent.putExtra(playerO, player_O.text.toString())
                intent.putExtra(playerX, player_X.text.toString())
                //starting the game
                startActivity(intent)
            } else {
                //if the players haven't entered their names then showing an Toast to notify them to enter
                //their names.
                Toast.makeText(
                    this, "Player names should not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
