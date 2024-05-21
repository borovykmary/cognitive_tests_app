package com.example.cognittiveassesmenttests.user_interaction


import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.cognittiveassesmenttests.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Class for handling user registration.
 *
 * @property context The context in which this class is being used.
 */
class RegisterUser(private val context: Context) {
    private lateinit var auth: FirebaseAuth


    /**
     * Registers a new user with the specified name, email, and password.
     *
     * @param name The name of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun registerUser(name: String, email: String, password: String) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // User profile updated successfully
                                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                } else {
                    // Registration failed
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}