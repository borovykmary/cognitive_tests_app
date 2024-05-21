package com.example.cognittiveassesmenttests

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.helpers.blurBitmap
import com.example.cognittiveassesmenttests.helpers.drawableToBitmap
import com.example.cognittiveassesmenttests.user_interaction.LoginUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.imageView)
        val bitmap = drawableToBitmap(R.drawable.bg_gradient, this)
        val blurredBitmap = blurBitmap(bitmap, this, 0.1f, 25f)
        imageView.setImageBitmap(blurredBitmap)


}
    }
