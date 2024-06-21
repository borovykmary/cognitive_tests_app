package com.example.cognittiveassesmenttests.mongoDB

import com.google.firebase.firestore.FirebaseFirestore

/**
 * A class that provides a connection to the Firestore database.
 *
 * This class is responsible for creating and returning an instance of the Firestore database.
 * It uses the Firebase Firestore SDK to connect to the database.
 */
class DBConnection {
    /**
     * Returns an instance of the Firestore database.
     *
     * @return FirebaseFirestore instance.
     */
    fun getFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}