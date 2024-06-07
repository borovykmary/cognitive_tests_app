package com.example.cognittiveassesmenttests.mongoDB.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: String,
    var name: String,
    var age: Int,
    var gender: String,
    @BsonProperty("firebase_user_id")
    var firebase_user_id: String
)
