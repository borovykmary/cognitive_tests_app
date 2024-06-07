package com.example.cognittiveassesmenttests.mongoDB.users


import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.mongodb.kotlin.client.coroutine.MongoDatabase


interface UsersDAO {
    suspend fun insertUser(database: MongoDatabase, user: User)
}
