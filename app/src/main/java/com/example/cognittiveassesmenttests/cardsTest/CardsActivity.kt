package com.example.cognittiveassesmenttests.cardsTest

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
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
import com.example.cognittiveassesmenttests.helpers.showConfirmPopup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//import androidx.activity.compose.setContent

class CardsActivity : AppCompatActivity() {
    private var dragCounter = 128 // 4 times more than needed, actually it is 64 times
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
        CardUiItem("card_blue_2_circle", "blue", 2, "circle"),
        CardUiItem("card_blue_3_circle", "blue", 3, "circle"),
        CardUiItem("card_blue_4_circle", "blue", 4, "circle"),
        CardUiItem("card_blue_1_star", "blue", 1, "star"),
        CardUiItem("card_blue_2_star", "blue", 2, "star"),
        CardUiItem("card_blue_3_star", "blue", 3, "star"),
        CardUiItem("card_blue_4_star", "blue", 4, "star"),
        CardUiItem("card_blue_1_triangle", "blue", 1, "triangle"),
        CardUiItem("card_blue_2_triangle", "blue", 2, "triangle"),
        CardUiItem("card_blue_3_triangle", "blue", 3, "triangle"),
        CardUiItem("card_blue_4_triangle", "blue", 4, "triangle"),
        CardUiItem("card_blue_1_square", "blue", 1, "square"),
        CardUiItem("card_blue_2_square", "blue", 2, "square"),
        CardUiItem("card_blue_3_square", "blue", 3, "square"),
        CardUiItem("card_blue_4_square", "blue", 4, "square"),
        CardUiItem("card_green_1_triangle", "green", 1, "triangle"),
        CardUiItem("card_green_2_triangle", "green", 2, "triangle"),
        CardUiItem("card_green_3_triangle", "green", 3, "triangle"),
        CardUiItem("card_green_4_triangle", "green", 4, "triangle"),
        CardUiItem("card_green_1_square", "green", 1, "square"),
        CardUiItem("card_green_2_square", "green", 2, "square"),
        CardUiItem("card_green_3_square", "green", 3, "square"),
        CardUiItem("card_green_4_square", "green", 4, "square"),
        CardUiItem("card_green_1_star", "green", 1, "star"),
        CardUiItem("card_green_2_star", "green", 2, "star"),
        CardUiItem("card_green_3_star", "green", 3, "star"),
        CardUiItem("card_green_4_star", "green", 4, "star"),
        CardUiItem("card_green_1_circle", "green", 1, "circle"),
        CardUiItem("card_green_2_circle", "green", 2, "circle"),
        CardUiItem("card_green_3_circle", "green", 3, "circle"),
        CardUiItem("card_green_4_circle", "green", 4, "circle"),
        CardUiItem("card_red_1_triangle", "red", 1, "triangle"),
        CardUiItem("card_red_2_triangle", "red", 2, "triangle"),
        CardUiItem("card_red_3_triangle", "red", 3, "triangle"),
        CardUiItem("card_red_4_triangle", "red", 4, "triangle"),
        CardUiItem("card_red_1_square", "red", 1, "square"),
        CardUiItem("card_red_2_square", "red", 2, "square"),
        CardUiItem("card_red_3_square", "red", 3, "square"),
        CardUiItem("card_red_4_square", "red", 4, "square"),
        CardUiItem("card_red_1_star", "red", 1, "star"),
        CardUiItem("card_red_2_star", "red", 2, "star"),
        CardUiItem("card_red_3_star", "red", 3, "star"),
        CardUiItem("card_red_4_star", "red", 4, "star"),
        CardUiItem("card_red_1_circle", "red", 1, "circle"),
        CardUiItem("card_red_2_circle", "red", 2, "circle"),
        CardUiItem("card_red_3_circle", "red", 3, "circle"),
        CardUiItem("card_red_4_circle", "red", 4, "circle"),
        CardUiItem("card_yellow_1_circle", "yellow", 1, "circle"),
        CardUiItem("card_yellow_2_circle", "yellow", 2, "circle"),
        CardUiItem("card_yellow_3_circle", "yellow", 3, "circle"),
        CardUiItem("card_yellow_4_circle", "yellow", 4, "circle"),
        CardUiItem("card_yellow_1_square", "yellow", 1, "square"),
        CardUiItem("card_yellow_2_square", "yellow", 2, "square"),
        CardUiItem("card_yellow_3_square", "yellow", 3, "square"),
        CardUiItem("card_yellow_4_square", "yellow", 4, "square"),
        CardUiItem("card_yellow_1_star", "yellow", 1, "star"),
        CardUiItem("card_yellow_2_star", "yellow", 2, "star"),
        CardUiItem("card_yellow_3_star", "yellow", 3, "star"),
        CardUiItem("card_yellow_4_star", "yellow", 4, "star"),
        CardUiItem("card_yellow_1_triangle", "yellow", 1, "triangle"),
        CardUiItem("card_yellow_2_triangle", "yellow", 2, "triangle"),
        CardUiItem("card_yellow_3_triangle", "yellow", 3, "triangle"),
        CardUiItem("card_yellow_4_triangle", "yellow", 4, "triangle")
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
        val textViewCounter = findViewById<TextView>(R.id.textViewCounter)

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

        showConfirmPopup(R.id.CardsBackButton, this, R.id.CardsActivity)

        val drawablesBlue = listOf(
            R.drawable.card_blue_1_circle,
            R.drawable.card_blue_2_circle,
            R.drawable.card_blue_3_circle,
            R.drawable.card_blue_4_circle,
            R.drawable.card_blue_1_star,
            R.drawable.card_blue_2_star,
            R.drawable.card_blue_3_star,
            R.drawable.card_blue_4_star,
            R.drawable.card_blue_1_triangle,
            R.drawable.card_blue_2_triangle,
            R.drawable.card_blue_3_triangle,
            R.drawable.card_blue_4_triangle,
            R.drawable.card_blue_1_square,
            R.drawable.card_blue_2_square,
            R.drawable.card_blue_3_square,
            R.drawable.card_blue_4_square,
        )

        val drawablesGreen = listOf(
            R.drawable.card_green_1_triangle,
            R.drawable.card_green_2_triangle,
            R.drawable.card_green_3_triangle,
            R.drawable.card_green_4_triangle,
            R.drawable.card_green_1_square,
            R.drawable.card_green_2_square,
            R.drawable.card_green_3_square,
            R.drawable.card_green_4_square,
            R.drawable.card_green_1_triangle,
            R.drawable.card_green_2_triangle,
            R.drawable.card_green_3_triangle,
            R.drawable.card_green_4_triangle,
            R.drawable.card_green_1_star,
            R.drawable.card_green_2_star,
            R.drawable.card_green_3_star,
            R.drawable.card_green_4_star,
        )

        val drawablesRed = listOf(
            R.drawable.card_red_1_triangle,
            R.drawable.card_red_2_triangle,
            R.drawable.card_red_3_triangle,
            R.drawable.card_red_4_triangle,
            R.drawable.card_red_1_square,
            R.drawable.card_red_2_square,
            R.drawable.card_red_3_square,
            R.drawable.card_red_4_square,
            R.drawable.card_red_1_star,
            R.drawable.card_red_2_star,
            R.drawable.card_red_3_star,
            R.drawable.card_red_4_star,
            R.drawable.card_red_1_circle,
            R.drawable.card_red_2_circle,
            R.drawable.card_red_3_circle,
            R.drawable.card_red_4_circle,
        )

        val drawablesYellow = listOf(
            R.drawable.card_yellow_1_circle,
            R.drawable.card_yellow_2_circle,
            R.drawable.card_yellow_3_circle,
            R.drawable.card_yellow_4_circle,
            R.drawable.card_yellow_1_square,
            R.drawable.card_yellow_2_square,
            R.drawable.card_yellow_3_square,
            R.drawable.card_yellow_4_square,
            R.drawable.card_yellow_1_star,
            R.drawable.card_yellow_2_star,
            R.drawable.card_yellow_3_star,
            R.drawable.card_yellow_4_star,
            R.drawable.card_yellow_1_triangle,
            R.drawable.card_yellow_2_triangle,
            R.drawable.card_yellow_3_triangle,
            R.drawable.card_yellow_4_triangle,
        )

        val answerDrawableIds = drawablesBlue + drawablesGreen + drawablesRed + drawablesYellow

        val imageViewAnswer = findViewById<ImageView>(R.id.imageViewAnswer)
        var answerDrawableId = answerDrawableIds.random()
        // Set the drawable for the imageViewAnswer
        imageViewAnswer.setImageResource(answerDrawableId)

        val targets = arrayOf(
            findViewById<ImageView>(R.id.imageViewTarget1),
            findViewById<ImageView>(R.id.imageViewTarget2),
            findViewById<ImageView>(R.id.imageViewTarget3),
            findViewById<ImageView>(R.id.imageViewTarget4)
        )
        var targetItems = shuffleTargets(targets)



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
                                textViewCounter.text = String.format("%02d/64", correctAnswers)
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
                        imageViewAnswer.setImageResource(answerDrawableId)

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
                            // Create a dataMap to store the number of correct answers and time
                            val dataMap = hashMapOf<String, Any>()
                            dataMap["CorrectAnswers"] = correctAnswers.toString()
                            dataMap["Time"] = textViewTime.text.toString()

                            // Send dataMap to the subcollection TestWC in a collection with a name that equals the Firebase user id
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            if (userId != null) {
                                val db = FirebaseFirestore.getInstance()
                                db.collection("Users").document(userId).collection("TestWC").document().set(dataMap)
                                    .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
                            }
                            // Create an intent to start MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            // Start MainActivity
                            startActivity(intent)
                            finish()
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