package com.example.cognittiveassesmenttests

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * This class represents the application and initializes Firebase when the application is created.
 */
class MyApplication : Application() {
    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}