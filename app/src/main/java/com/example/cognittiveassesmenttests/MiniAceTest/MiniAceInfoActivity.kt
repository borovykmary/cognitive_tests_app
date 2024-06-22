package com.example.cognittiveassesmenttests.MiniAceTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.MainActivity

/**
 * This activity provides information about the MiniAce test.
 * It includes a back button to return to the main activity and a button to start the MiniAce test.
 * It also handles edge-to-edge screen display.
 */
class MiniAceInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mini_ace_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MiniAceinfoActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val miniAceInfoBackButton = findViewById<ImageView>(R.id.miniAceInfoBackButton)
        miniAceInfoBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val miniAceButton = findViewById<Button>(R.id.buttonTestMA)
        miniAceButton.setOnClickListener {
            val intent = Intent(this, MiniAceTestActivity::class.java)
            startActivity(intent)
        }
    }


}