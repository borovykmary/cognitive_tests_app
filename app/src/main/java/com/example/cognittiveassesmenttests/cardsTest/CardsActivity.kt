package com.example.cognittiveassesmenttests.cardsTest

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    private var dragCounter = 256 // 4 times more than needed, actually it is 64 times

    private fun extractPropertiesFromResourceName(resourceName: String): CardUiItem {
        val properties = resourceName.split("_")
        val color = properties[1]
        val number = properties[2].toInt()
        val shape = properties[3]
        return CardUiItem(resourceName, color, number, shape)
    }

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

        val drawablesBlue = listOf(
            R.drawable.card_blue_1_circle,
            R.drawable.card_blue_3_circle,
            R.drawable.card_blue_4_star
        )

        val drawablesGreen = listOf(
            R.drawable.card_green_1_triangle,
            R.drawable.card_green_2_square,
            R.drawable.card_green_2_triangle
        )

        val drawablesRed = listOf(
            R.drawable.card_red_1_triangle,
            R.drawable.card_red_2_square,
            R.drawable.card_red_3_star
        )

        val drawablesYellow = listOf(
            R.drawable.card_yellow_3_circle,
            R.drawable.card_yellow_4_square,
            R.drawable.card_yellow_4_star
        )

        val answerDrawableIds = drawablesBlue + drawablesGreen + drawablesRed + drawablesYellow

        val imageViewAnswer = findViewById<ImageView>(R.id.imageViewAnswer)
        val targets = arrayOf(
            findViewById<ImageView>(R.id.imageViewTarget1),
            findViewById<ImageView>(R.id.imageViewTarget2),
            findViewById<ImageView>(R.id.imageViewTarget3),
            findViewById<ImageView>(R.id.imageViewTarget4)
        )

        val targetDrawableIds = listOf(
            R.drawable.card_blue_1_circle,
            R.drawable.card_green_2_square,
            R.drawable.card_red_3_star,
            R.drawable.card_yellow_4_star
        )
        var answerDrawableId = answerDrawableIds.random()
        // Set the drawable for the imageViewAnswer
        imageViewAnswer.setImageResource(answerDrawableId)

        // Set the drawables for the targets
        targets.forEachIndexed { index, target ->
            target.setImageResource(targetDrawableIds[index])
        }

        // Create a CardUiItem for each target
        val targetItems = targetDrawableIds.map { drawableId ->
            extractPropertiesFromResourceName(resources.getResourceEntryName(drawableId))
        }

        imageViewAnswer.setOnLongClickListener { v ->
            val clipData = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadowBuilder, v, 0)
            v.scaleX = 0.8f
            v.scaleY = 0.8f
            true
        }


        targets.forEachIndexed {index, target ->
            target.setImageResource(targetDrawableIds[index])
            // Create a CardUiItem for the current target
            val currentItem = targetItems[index]
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

                        Log.d("CardsActivity", "AnswerImageId: " + answerDrawableId)
                        Log.d("CardsActivity", "AnswerImageResource: " + resources.getResourceEntryName(answerDrawableId))
                        // Now you can use answerDrawableId to get the resource name
                        val answerItem = extractPropertiesFromResourceName(resources.getResourceEntryName(answerDrawableId))
                        Log.d("CardsActivity", "AnswerImage: " + answerItem.color)
                        Log.d("CardsActivity", "CurrentImage: " + currentItem.color)
                        // Check if the item being dropped is the imageViewAnswer
                        if (event.localState === imageViewAnswer) {
                            // Check if the properties match
                            if (answerItem.color == currentItem.color) {
                                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                            }
                        }
                        answerDrawableId = answerDrawableIds.random()
                        // Assign a new drawable resource to imageViewAnswer
                        imageViewAnswer.setImageResource(answerDrawableId)
                        true
                    }

                    DragEvent.ACTION_DRAG_ENDED -> {
                        dragCounter--
                        // Check if the drag counter has reached 0
                        if (dragCounter == 0) {
                            // Display a toast message
                            Toast.makeText(this, "No more drags allowed", Toast.LENGTH_SHORT).show()

                            // Disable further dragging
                            imageViewAnswer.setOnLongClickListener(null)
                        } else {
                            // Revert any remaining style changes
                            imageViewAnswer.scaleX = 1f
                            imageViewAnswer.scaleY = 1f

                            // Shuffle the lists of drawable resources
                            val shuffledDrawablesBlue = drawablesBlue.shuffled()
                            val shuffledDrawablesGreen = drawablesGreen.shuffled()
                            val shuffledDrawablesRed = drawablesRed.shuffled()
                            val shuffledDrawablesYellow = drawablesYellow.shuffled()

                            // Assign the shuffled drawable resources to the targets
                            targets[0].setImageResource(shuffledDrawablesBlue[0])
                            targets[1].setImageResource(shuffledDrawablesGreen[0])
                            targets[2].setImageResource(shuffledDrawablesRed[0])
                            targets[3].setImageResource(shuffledDrawablesYellow[0])
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }
}