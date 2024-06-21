package com.example.cognittiveassesmenttests.SDMTTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R

/**
 * This activity provides information about the SDMT test.
 * It includes a back button to return to the main activity and a button to start the SDMT test.
 * It also handles edge-to-edge screen display.
 */
class SDMTInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sdmtinfo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SDMTInfoActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val SDMTInfoBackButton = findViewById<ImageView>(R.id.SDMTBackButton)
        SDMTInfoBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val SDMTInfoTestButton = findViewById<Button>(R.id.buttonSDMTActivity)
        SDMTInfoTestButton.setOnClickListener {

            val intent = Intent(this, SDMTActivity::class.java)
            startActivity(intent)
        }
    }
}