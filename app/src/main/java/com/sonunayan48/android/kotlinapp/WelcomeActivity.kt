package com.sonunayan48.android.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private val playerO: String = "playerO"
    private val playerX: String = "playerX"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        icon.animation = AnimationUtils.loadAnimation(this, R.anim.moving_up)
        val handler: Handler = Handler()
        handler.postDelayed(
            {
                linearLayout.visibility = View.VISIBLE
                linearLayout2.visibility = View.VISIBLE
                playBtn.visibility = View.VISIBLE
            },
            2000
        )
        playBtn.setOnClickListener {
            if (player_O.text.toString().isNotEmpty()
                && player_X.text.toString().isNotEmpty()
            ) {
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.putExtra(playerO, player_O.text.toString())
                intent.putExtra(playerX, player_X.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(
                    this, "Player names should not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
