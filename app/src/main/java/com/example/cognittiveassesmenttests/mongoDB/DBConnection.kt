package com.example.cognittiveassesmenttests.mongoDB

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DBConnection {
    fun getFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}