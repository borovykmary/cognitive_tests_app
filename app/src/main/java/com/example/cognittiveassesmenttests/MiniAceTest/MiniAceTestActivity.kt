package com.example.cognittiveassesmenttests.MiniAceTest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cognittiveassesmenttests.R

class MiniAceTestActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var imageViewCounter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mini_ace_test)

        viewPager = findViewById(R.id.viewPagerQuestions)
        viewPager.adapter = MiniAceTestAdapter(this)

        imageViewCounter = findViewById(R.id.imageViewCounter)

        val nextButton: ImageView = findViewById(R.id.next_button_image)
        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < 4) { // Replace 4 with the number of fragments - 1
                viewPager.setCurrentItem(currentItem + 1)
                updateCounterImage(currentItem + 1)
            }
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
    }

    private fun updateCounterImage(counter: Int) {
        val drawableRes = when (counter) {
            0 -> R.drawable.number_quiz_1
            1 -> R.drawable.number_quiz_2
            2 -> R.drawable.number_quiz_3
            3 -> R.drawable.number_quiz_4
            4 -> R.drawable.number_quiz_5
            else -> throw IllegalStateException("Invalid counter for updating image")
        }
        imageViewCounter.setImageResource(drawableRes)
    }
}
