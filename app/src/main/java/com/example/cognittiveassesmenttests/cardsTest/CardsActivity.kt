package com.example.cognittiveassesmenttests.cardsTest

import android.content.ClipData
import android.content.Intent
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
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R
//import androidx.activity.compose.setContent

class CardsActivity : AppCompatActivity() {
    private var dragCounter = 256 // 4 times more than needed, actually it is 64 times
    private var correctAnswersInRow = 0
    private var currentCondition = "color"
    private var correctAnswers = 0
    private var secondsElapsed = 0

    private fun extractPropertiesFromResourceName(resourceName: String): CardUiItem {
        val properties = resourceName.split("_")
        val color = properties[1]
        val number = properties[2].toInt()
        val shape = properties[3]
        return CardUiItem(resourceName, color, number, shape)
    }

    private fun shuffleTargets(targets: Array<ImageView>): List<CardUiItem> {
        Log.d("CardsActivity", "Shuffling: ")
    // Create a list of all possible CardUiItem objects
    val allItems = mutableListOf(
        CardUiItem("card_blue_1_circle", "blue", 1, "circle"),
        CardUiItem("card_blue_3_circle", "blue", 3, "circle"),
        CardUiItem("card_blue_4_star", "blue", 4, "star"),
        CardUiItem("card_green_1_triangle", "green", 1, "triangle"),
        CardUiItem("card_green_2_square", "green", 2, "square"),
        CardUiItem("card_green_2_triangle", "green", 2, "triangle"),
        CardUiItem("card_red_1_triangle", "red", 1, "triangle"),
        CardUiItem("card_red_2_square", "red", 2, "square"),
        CardUiItem("card_red_3_star", "red", 3, "star"),
        CardUiItem("card_yellow_3_circle", "yellow", 3, "circle"),
        CardUiItem("card_yellow_4_square", "yellow", 4, "square"),
        CardUiItem("card_yellow_4_star", "yellow", 4, "star")
    )

    val result = mutableListOf<CardUiItem>()

    while (result.size < 4) {
        allItems.shuffle()
        val candidate = allItems.first()

        if (result.none { it.color == candidate.color || it.shape == candidate.shape || it.number == candidate.number }) {
            result.add(candidate)
            Log.d("CardsActivity", "candidate: " + candidate)
        }
    }

    // Set the drawables for the targets
    targets.forEachIndexed { index, target ->
        val drawableId = resources.getIdentifier(result[index].resource, "drawable", packageName)
        target.setImageResource(drawableId)
    }

    // Create a CardUiItem for each target
    var targetItems = result.map { item ->
        extractPropertiesFromResourceName(item.resource)
    }

    return targetItems
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
        var targetItems = shuffleTargets(targets)

        var answerDrawableId = answerDrawableIds.random()
        // Set the drawable for the imageViewAnswer
        imageViewAnswer.setImageResource(answerDrawableId)

        imageViewAnswer.setOnLongClickListener { v ->
            val clipData = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadowBuilder, v, 0)
            v.scaleX = 0.8f
            v.scaleY = 0.8f
            true
        }


        targets.forEachIndexed {index, target ->
            // Create a CardUiItem for the current target
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
                        val currentItem = targetItems[index]
                        // Now you can use answerDrawableId to get the resource name
                        val answerItem = extractPropertiesFromResourceName(resources.getResourceEntryName(answerDrawableId))
                        // Check if the item being dropped is the imageViewAnswer
                        if (event.localState === imageViewAnswer) {
                            val isCorrect = when (currentCondition) {
                                "color" -> answerItem.color == currentItem.color
                                "shape" -> answerItem.shape == currentItem.shape
                                "number" -> answerItem.number == currentItem.number
                                else -> false
                            }

                            if (isCorrect) {
                                correctAnswersInRow++
                                correctAnswers++
                                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                correctAnswersInRow = 0
                                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                            }

                            // If 10 correct answers in a row, randomly change the condition
                            if (correctAnswersInRow == 10) {
                                currentCondition = when (currentCondition) {
                                    "color" -> if (Math.random() < 0.5) "shape" else "number"
                                    "shape" -> if (Math.random() < 0.5) "color" else "number"
                                    "number" -> if (Math.random() < 0.5) "color" else "shape"
                                    else -> currentCondition
                                }
                                correctAnswersInRow = 0
                            }
                            Log.d("CardsActivity", "AnswerImageId: " + answerDrawableId)
                            Log.d("CardsActivity", "AnswerImageResource: " + resources.getResourceEntryName(answerDrawableId))
                            Log.d("CardsActivity", "CurrentImageResource: " + currentItem.resource)
                            Log.d("CardsActivity", "AnswerImageColor: " + answerItem.color)
                            Log.d("CardsActivity", "CurrentImageColor: " + currentItem.color)
                            Log.d("CardsActivity", "AnswerImageShape: " + answerItem.shape)
                            Log.d("CardsActivity", "CurrentImageShape: " + currentItem.shape)
                            Log.d("CardsActivity", "AnswerImageNumber: " + answerItem.number)
                            Log.d("CardsActivity", "CurrentImageNumber: " + currentItem.number)
                            Log.d("CardsActivity", "Condition: " + currentCondition)
                            Log.d("CardsActivity", "Correctinrow: " + correctAnswersInRow)

                        }
                        answerDrawableId = answerDrawableIds.random()

                        targetItems = shuffleTargets(targets)

                        true
                    }

                    DragEvent.ACTION_DRAG_ENDED -> {
                        dragCounter--
                        // Check if the drag counter has reached 0
                        if (dragCounter == 0) {
                            // Display a toast message with correct answers and elapsed time
                            Toast.makeText(this, "No more drags allowed. Correct answers: $correctAnswers. Time: ${textViewTime.text}", Toast.LENGTH_SHORT).show()

                            // Disable further dragging
                            imageViewAnswer.setOnLongClickListener(null)
                            // Create an intent to start MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            // Start MainActivity
                            startActivity(intent)
                        } else {
                            // Revert any remaining style changes
                            imageViewAnswer.scaleX = 1f
                            imageViewAnswer.scaleY = 1f

                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }
}