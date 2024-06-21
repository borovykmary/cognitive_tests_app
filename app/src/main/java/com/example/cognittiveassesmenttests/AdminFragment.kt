package com.example.cognittiveassesmenttests

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterAdminMA
import com.google.firebase.firestore.FirebaseFirestore

/**
 * This fragment is accessible only to admin users.
 * It provides functionality for managing the application, such as adding new tests or viewing user data.
 */
class AdminFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.testRecordsRecycleViewMA)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val db = FirebaseFirestore.getInstance()
        db.collection("Users").get().addOnSuccessListener { users ->
            val testData = mutableListOf<Map<String, Any>>()
            for (user in users) {
                val userId = user.id
                db.collection("Users").document(userId).collection("TestMA").get().addOnSuccessListener { tests ->
                    for (test in tests) {
                        val dataWithId = test.data.toMutableMap()
                        // Check if the document has the field "MEMORY"
                        if (dataWithId.containsKey("MEMORY")) {
                            continue // Skip this document
                        }
                        dataWithId["id"] = test.id // Add the document ID to the data map
                        testData.add(dataWithId)
                        Log.d("DATAACTIVITY", "DATA: " + dataWithId["id"])
                    }
                    val adapter = TestRecordAdapterAdminMA(testData, childFragmentManager) { data ->
                        // Handle confirm action
                    }
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }


}