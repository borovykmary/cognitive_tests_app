package com.example.cognittiveassesmenttests.mongoDB.users

import com.example.cognittiveassesmenttests.mongoDB.model.User
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject

class UsersQueries : UsersDAO {
    private val client = OkHttpClient()

    override suspend fun insertUser(user: User) {
        val jsonObject = JSONObject()
        jsonObject.put("firebase_user_id", user.firebase_user_id)
        jsonObject.put("name", user.name)
        jsonObject.put("age", user.age)
        jsonObject.put("gender", user.gender)

        val requestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val credentials = Credentials.basic("app", "Passw0rd") // replace with your MongoDB Atlas username and password

        val request = Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/application-0-htbjflu/endpoint/insertUser")
            .addHeader("Authorization", credentials)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            println(response.body?.string())
        }
    }
}