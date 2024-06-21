package com.example.cognittiveassesmenttests.mongoDB.model


class User {
    var firebase_user_id: String? = null
    var name: String = ""
    var age: Int = 18
    var gender: String? = null
    val role: String = "ROLE_READER"
}
