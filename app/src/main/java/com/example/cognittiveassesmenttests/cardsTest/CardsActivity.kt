package com.example.cognittiveassesmenttests.cardsTest

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
/*import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color*/
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.R
//import androidx.activity.compose.setContent

class CardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CardsActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewTime = findViewById<TextView>(R.id.textViewTime)

        // Initialize a new Handler instance
        val handler = Handler(Looper.getMainLooper())
        var secondsElapsed = 0

        val runnable = object : Runnable {
            override fun run() {
                val minutes = secondsElapsed / 60
                val seconds = secondsElapsed % 60
                textViewTime.text = String.format("%02d:%02d", minutes, seconds)
                secondsElapsed++
                handler.postDelayed(this, 1000)
            }
        }

        // Start the timer
        handler.postDelayed(runnable, 1000)


        val imageViewAnswer = findViewById<ImageView>(R.id.imageViewAnswer)
        val targets = arrayOf(
            findViewById<ImageView>(R.id.imageViewTarget1),
            findViewById<ImageView>(R.id.imageViewTarget2),
            findViewById<ImageView>(R.id.imageViewTarget3),
            findViewById<ImageView>(R.id.imageViewTarget4)
        )

        imageViewAnswer.setOnLongClickListener { v ->
            val clipData = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadowBuilder, v, 0)
            v.scaleX = 0.8f
            v.scaleY = 0.8f
            true
        }

        targets.forEach { target ->
            target.setOnDragListener { v, event ->
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        // Do nothing
                        true
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                        // Optional: change the style of the target to indicate that it's an active drop area
                        true
                    }
                    DragEvent.ACTION_DRAG_EXITED -> {
                        // Optional: revert the style change
                        true
                    }
                    DragEvent.ACTION_DROP -> {
                        // Check if the item being dropped is the imageViewAnswer
                        if (event.localState === imageViewAnswer) {
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                    DragEvent.ACTION_DRAG_ENDED -> {
                        // Revert any remaining style changes
                        imageViewAnswer.scaleX = 1f
                        imageViewAnswer.scaleY = 1f
                        true
                    }
                    else -> false
                }
            }
        }
    }
}