package com.example.cognittiveassesmenttests.user_interaction

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Class for handling user login.
 *
 * @property activity The activity in which this class is being used.
 */
class LoginUser(private val activity: Activity) {
    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient

    /**
     * Logs in a user with the specified email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun loginUser(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // User logged in successfully
                Toast.makeText(activity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
                } else {
                // Login failed
                Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show()
            }
            }
        }


    /**
     * Initiates the Google Sign In process.
     */
    fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * Handles the result of the Google Sign In process.
     *
     * @param requestCode The request code of the sign in process.
     * @param resultCode The result code of the sign in process.
     * @param data The intent data from the sign in process.
     */
    fun handleSignInResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed
            }
        }
    }

    /**
     * Authenticates a user with Google.
     *
     * @param acct The GoogleSignInAccount of the user.
     */
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User logged in successfully
                    Toast.makeText(activity, "Logged in successfully with Google", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                } else {
                    // Login failed
                    Toast.makeText(activity, "Google login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
