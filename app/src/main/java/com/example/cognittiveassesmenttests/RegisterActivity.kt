package com.example.cognittiveassesmenttests

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.helpers.blurBitmap
import com.example.cognittiveassesmenttests.helpers.drawableToBitmap
import com.example.cognittiveassesmenttests.user_interaction.RegisterUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerUser: RegisterUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MiniAceinfoBackButton)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.imageView)
        val bitmap = drawableToBitmap(R.drawable.bg_gradient, this)
        val blurredBitmap = blurBitmap(bitmap, this, 0.1f, 25f)
        imageView.setImageBitmap(blurredBitmap)

        registerUser = RegisterUser(this)

        val nameEditText = findViewById<EditText>(R.id.editTextNameRegister)
        val emailEditText = findViewById<EditText>(R.id.editTextEmailAddressRegister)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordRegister)
        val passwordRepeatEditText = findViewById<EditText>(R.id.editTextPasswordRegisterRepeat)
        val ageEditText = findViewById<EditText>(R.id.editTextNameRegisterAge)
        val genderEditText = findViewById<EditText>(R.id.editTextNameRegisterGender)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordRepeat = passwordRepeatEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull()
            val gender = genderEditText.text.toString()
            if (password == passwordRepeat) {
                if (gender == "Male" || gender == "Female") {
                    if (age in 1..100) {
                        registerUser.registerUser(name, email, password, age.toString(), gender)
                    } else {
                        Toast.makeText(this, "Age must be between 1 and 100", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Gender must be either Male or Female", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }

        val AccountLoginClick = findViewById<TextView>(R.id.textViewAccountLoginClick)
        AccountLoginClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

}
    }
