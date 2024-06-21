package com.example.cognittiveassesmenttests.mongoDB.model

/**
 * A data class that represents a user in the system.
 *
 * This class holds the user's data, such as username, email, and other user-specific information.
 */
class User {
    var firebase_user_id: String? = null
    var name: String = ""
    var age: Int = 18
    var gender: String? = null
    val role: String = "ROLE_READER"
}
