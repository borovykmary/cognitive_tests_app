package com.example.cognittiveassesmenttests.mongoDB.model


import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

//Mongo Realm Endpoint
class User : RealmObject {
    @PrimaryKey
    var firebase_user_id: String? = null
    @Index
    var name: String = ""
    var age: Int = 18
    var gender: String? = null
}
// MongoDB Driver
/*data class User(
    @BsonId
    val id: String,
    var name: String,
    var age: Int,
    var gender: String,
    @BsonProperty("firebase_user_id")
    var firebase_user_id: String
)*/
