package com.example.cognittiveassesmenttests.mongoDB.users


import com.example.cognittiveassesmenttests.mongoDB.model.User


interface UsersDAO {

    //Mongo Realm Endpoint
    suspend fun insertUser(user: User)
    //MongoDB Driver
    //suspend fun insertUser(database: MongoDatabase, user: User)
}
