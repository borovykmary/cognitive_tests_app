package com.example.cognittiveassesmenttests.SDMTTest

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.helpers.showConfirmPopup
import com.example.cognittiveassesmenttests.helpers.showConfirmPopupTest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * This activity handles the SDMT test.
 * It includes a countdown timer for the test duration and displays the remaining time.
 * It also handles the logic for the test, including shuffling the keys and checking the user's answers.
 * The results of the test are stored in a Firestore database.
 * It also handles edge-to-edge screen display.
 */
class SDMTActivity : AppCompatActivity() {
    // Create a map to store the mapping of image views to drawable resources
    private val imageViewToDrawableMap = mutableMapOf<ImageView, Int>()
    private var currentKey = 0
    private var correctAnswers = 0 // Counter for correct answers
    private var totalAnswers = 0

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

                val correctPercentage = if (totalAnswers > 0) (correctAnswers.toDouble() / totalAnswers) * 100 else 0.0
                val roundedPercentage = String.format("%.1f", correctPercentage)
                // Create a dataMap to store the number of correct answers
                val dataMap = hashMapOf<String, Any>()
                val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                val currentDate = sdf.format(Date())
                dataMap["Date"] = currentDate
                dataMap["CorrectPercentage"] = roundedPercentage.toString()
                dataMap["CorrectAnswers"] = correctAnswers.toString()
                dataMap["AllAnswers"] = totalAnswers.toString()

                // Send dataMap to the subcollection TestSDMT in a collection with a name that equals the Firebase user id
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val db = FirebaseFirestore.getInstance()
                    db.collection("Users").document(userId).collection("TestSDMT").document().set(dataMap)
                        .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
                }
                showConfirmPopupTest(this@SDMTActivity, R.id.SDMTTestActivity)

            }
        }

        // Start the countdown
        countDownTimer.start()

        // Create a list of all the image views
        val imageViews = listOf(
            findViewById<ImageView>(R.id.imageViewKey1),
            findViewById(R.id.imageViewKey2),
            findViewById(R.id.imageViewKey3),
            findViewById(R.id.imageViewKey4),
            findViewById(R.id.imageViewKey5),
            findViewById(R.id.imageViewKey6),
            findViewById(R.id.imageViewKey7),
            findViewById(R.id.imageViewKey8),
            findViewById(R.id.imageViewKey9)
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
            findViewById(R.id.textViewKey2),
            findViewById(R.id.textViewKey3),
            findViewById(R.id.textViewKey4),
            findViewById(R.id.textViewKey5),
            findViewById(R.id.textViewKey6),
            findViewById(R.id.textViewKey7),
            findViewById(R.id.textViewKey8),
            findViewById(R.id.textViewKey9)
        )

        for (i in textViewKeys.indices) {
            textViewKeys[i].setOnClickListener {
                totalAnswers++
                // Check if the clicked key matches the current key in imageViewKeyQuestion
                if (imageViewToDrawableMap[imageViews[i]] == currentKey) {
                    correctAnswers++
                }

                // Trigger a click on imageViewNumbersAnswer to shuffle everything
                imageViewNumbersAnswer.performClick()
            }
        }
        // Shuffle once at the start of the activity
        imageViewNumbersAnswer.performClick()
    }
}