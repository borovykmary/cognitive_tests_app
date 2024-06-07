package com.example.cognittiveassesmenttests.mongoDB.users

import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.BsonString
import org.bson.types.ObjectId


class UsersQueries : UsersDAO {
    override suspend fun insertUser(database: MongoDatabase, user: User) {
    val collection = database.getCollection<BsonDocument>(collectionName = "Users")
    val item = BsonDocument()
        .append("_id", BsonString(ObjectId().toHexString()))
        .append("name", BsonString(user.name))
        .append("age", BsonInt32(user.age))
        .append("gender", BsonString(user.gender))
        .append("firebase_user_id", BsonString(user.firebase_user_id))

    collection.insertOne(item).also {
        println("User added with id - ${it.insertedId}")
    }
}
}