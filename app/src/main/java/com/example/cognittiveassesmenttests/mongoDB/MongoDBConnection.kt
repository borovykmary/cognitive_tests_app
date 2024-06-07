package com.example.cognittiveassesmenttests.mongoDB

import com.example.cognittiveassesmenttests.mongoDB.users.UsersQueries
import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.bson.BsonInt64
import org.bson.Document

    suspend fun setupConnection(
        databaseName: String = "cognitiveapp"
    ): MongoDatabase? {
        val connectString = "mongodb+srv://app:Passw0rd@cognitiveapp.nqus5wg.mongodb.net/?retryWrites=true&w=majority&appName=CognitiveApp"

        val client = MongoClient.create(connectionString = connectString)
        val database = client.getDatabase(databaseName = databaseName)

        return try {
            // Send a ping to confirm a successful connection
            val command = Document("ping", BsonInt64(1))
            database.runCommand(command)
            println("Pinged your deployment. You successfully connected to MongoDB!")
            database
        } catch (me: MongoException) {
            System.err.println(me)
            null
        }
    }

    suspend fun listAllCollection(database: MongoDatabase) {

        val count = database.listCollectionNames().count()
        println("Collection count $count")

        print("Collection in this database are -----------> ")
        database.listCollectionNames().collect { print(" $it") }
    }

fun main() {
    runBlocking {
        setupConnection()?.let { db: MongoDatabase ->
            val usersQueries = UsersQueries()
            //usersQueries.insertUser(database = db)
        }
    }
}
