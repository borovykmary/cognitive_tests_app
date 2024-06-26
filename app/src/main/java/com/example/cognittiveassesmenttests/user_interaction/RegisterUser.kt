package com.example.cognittiveassesmenttests.user_interaction


import UsersQueries
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.cognittiveassesmenttests.LoginActivity
import com.example.cognittiveassesmenttests.mongoDB.DBConnection
import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    fun registerUser(name: String, email: String, password: String, age: String, gender: String) {
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

                                // Create an instance of DBConnection
                                val dbConnection = DBConnection()

                                // Create an instance of UsersQueries
                                val usersQueries = UsersQueries(dbConnection)

                                val newUser = User().apply {
                                    firebase_user_id = user.uid
                                    this.name = name
                                    this.age = age.toInt()
                                    this.gender = gender
                                }

                                    usersQueries.insertUser(newUser)




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