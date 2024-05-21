package com.example.cognittiveassesmenttests

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
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

class LoginActivity : AppCompatActivity() {
    private lateinit var loginUser: LoginUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.imageView2)
        val bitmap = drawableToBitmap(R.drawable.bg_gradient, this)
        val blurredBitmap = blurBitmap(bitmap, this, 0.1f, 25f)
        imageView.setImageBitmap(blurredBitmap)

        loginUser = LoginUser(this)

        val emailEditText = findViewById<EditText>(R.id.editTextEmailAddressLogin)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val googleSignInButton = findViewById<Button>(R.id.buttonGoogleRegister)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginUser.loginUser(email, password)
        }

        googleSignInButton.setOnClickListener {
            loginUser.googleSignIn()
        }

        val AccountRegisterClick = findViewById<TextView>(R.id.textViewAccountRegisterClick)
        AccountRegisterClick.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Called when an activity you launched exits.
     *
     * This method handles the result of the sign in process.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     * allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its setResult().
     * @param data An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginUser.handleSignInResult(requestCode, resultCode, data)
    }
}
