package com.example.cognittiveassesmenttests.cardsTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.HomeFragment
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R

/**
 * This activity provides information about the Cards test.
 * It includes a back button to return to the main activity and a button to start the Cards test.
 */
class CardInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CardsInfoActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val CardsInfoBackButton = findViewById<ImageView>(R.id.CardsInfoBackButton)
        CardsInfoBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val CardsInfoTestButton = findViewById<Button>(R.id.buttonTestNextCards)
        CardsInfoTestButton.setOnClickListener {
            val intent = Intent(this, CardsActivity::class.java)
            startActivity(intent)
        }
    }
}