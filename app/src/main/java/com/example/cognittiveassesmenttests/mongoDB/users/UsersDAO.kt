package com.example.cognittiveassesmenttests.mongoDB.users


import com.example.cognittiveassesmenttests.mongoDB.model.User
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface UsersDAO {
    suspend fun insertUser(user: User)
}