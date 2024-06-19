package com.example.cognittiveassesmenttests

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterAdminMA

import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.testRecordsRecycleViewMA)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val db = FirebaseFirestore.getInstance()
        db.collection("Users").get().addOnSuccessListener { users ->
            val testData = mutableListOf<Map<String, Any>>()
            for (user in users) {
                val userId = user.id
                db.collection("Users").document(userId).collection("TestMA").get().addOnSuccessListener { tests ->
                    for (test in tests) {
                        testData.add(test.data)
                    }
                    val adapter = TestRecordAdapterAdminMA(testData) { data ->
                        // Handle confirm action
                    }
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}