package com.example.cognittiveassesmenttests

import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.example.cognittiveassesmenttests.mongoDB.users.UsersQueries
import kotlinx.coroutines.runBlocking

fun main() {
    val usersQueries = UsersQueries()

    val user = User().apply {
        firebase_user_id = "test_user_id"
        name = "Test User"
        age = 30
        gender = "Male"
    }

    println("Inserting user: ${user.name}, Age: ${user.age}, Gender: ${user.gender}")

    runBlocking {
        usersQueries.insertUser(user)
    }

    println("User inserted successfully")
}