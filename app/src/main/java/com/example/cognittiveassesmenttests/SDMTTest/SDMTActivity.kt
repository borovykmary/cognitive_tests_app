package com.example.cognittiveassesmenttests.SDMTTest

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.helpers.showConfirmPopup

class SDMTActivity : AppCompatActivity() {
    // Create a map to store the mapping of image views to drawable resources
    private val imageViewToDrawableMap = mutableMapOf<ImageView, Int>()
    private var currentKey = 0
    private var correctAnswers = 0 // Counter for correct answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sdmtactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SDMTTestActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textViewTime = findViewById<TextView>(R.id.textViewTime)

        // Initialize a new CountDownTimer instance
        val countDownTimer = object : CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                textViewTime.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                textViewTime.text = "00:00"
                // Show the number of correct answers in a Toast message
                Toast.makeText(this@SDMTActivity, "Correct answers: $correctAnswers", Toast.LENGTH_LONG).show()
                // Start MainActivity
                val intent = Intent(this@SDMTActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Start the countdown
        countDownTimer.start()

        // Create a list of all the image views
        val imageViews = listOf(
            findViewById<ImageView>(R.id.imageViewKey1),
            findViewById<ImageView>(R.id.imageViewKey2),
            findViewById<ImageView>(R.id.imageViewKey3),
            findViewById<ImageView>(R.id.imageViewKey4),
            findViewById<ImageView>(R.id.imageViewKey5),
            findViewById<ImageView>(R.id.imageViewKey6),
            findViewById<ImageView>(R.id.imageViewKey7),
            findViewById<ImageView>(R.id.imageViewKey8),
            findViewById<ImageView>(R.id.imageViewKey9)
        )

        // Create a list of all the drawable resources
        val drawables = listOf(
            R.drawable.key_1,
            R.drawable.key_2,
            R.drawable.key_3,
            R.drawable.key_4,
            R.drawable.key_5,
            R.drawable.key_6,
            R.drawable.key_7,
            R.drawable.key_8,
            R.drawable.key_9
        )

        // Find the imageViewNumbersAnswer and set its click listener
        val imageViewNumbersAnswer = findViewById<ImageView>(R.id.imageViewNumbersAnswer)
        imageViewNumbersAnswer.setOnClickListener {
            // Shuffle the list of drawable resources
            val shuffledDrawables = drawables.shuffled()

            // Assign the shuffled drawable resources to the image views
            for (i in imageViews.indices) {
                imageViews[i].setImageResource(shuffledDrawables[i])

                // Store the mapping of image views to drawable resources
                imageViewToDrawableMap[imageViews[i]] = shuffledDrawables[i]
            }

            // Shuffle the key in imageViewKeyQuestion
            val imageViewKeyQuestion = findViewById<ImageView>(R.id.imageViewKeyQuestion)
            currentKey = shuffledDrawables.random()
            imageViewKeyQuestion.setImageResource(currentKey)
        }

        showConfirmPopup(R.id.SDMTBackButton, this, R.id.SDMTTestActivity)
        // Set click listeners for the textViewKey views
        val textViewKeys = listOf(
            findViewById<TextView>(R.id.textViewKey1),
            findViewById<TextView>(R.id.textViewKey2),
            findViewById<TextView>(R.id.textViewKey3),
            findViewById<TextView>(R.id.textViewKey4),
            findViewById<TextView>(R.id.textViewKey5),
            findViewById<TextView>(R.id.textViewKey6),
            findViewById<TextView>(R.id.textViewKey7),
            findViewById<TextView>(R.id.textViewKey8),
            findViewById<TextView>(R.id.textViewKey9)
        )

        for (i in textViewKeys.indices) {
            textViewKeys[i].setOnClickListener {
                // Check if the clicked key matches the current key in imageViewKeyQuestion
                if (imageViewToDrawableMap[imageViews[i]] == currentKey) {
                    correctAnswers++
                    Toast.makeText(this, "Right answer", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
                }

                // Trigger a click on imageViewNumbersAnswer to shuffle everything
                imageViewNumbersAnswer.performClick()
            }
        }
        // Shuffle once at the start of the activity
        imageViewNumbersAnswer.performClick()
    }
}