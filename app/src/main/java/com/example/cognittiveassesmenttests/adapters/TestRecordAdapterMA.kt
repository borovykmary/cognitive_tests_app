package com.example.cognittiveassesmenttests.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.dataClasses.TestRecordMA

class TestRecordAdapterMA(private val testRecords: List<TestRecordMA>) : RecyclerView.Adapter<TestRecordAdapterMA.TestRecordViewHolder>() {

    class TestRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testName: TextView = view.findViewById(R.id.TestResultMA)
        val testDate: TextView = view.findViewById(R.id.time)
        val testDetails: TextView = view.findViewById(R.id.seeDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_record_item_ma, parent, false)
        return TestRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestRecordViewHolder, position: Int) {
        Log.d("RecordsFragment", "Test records: $testRecords")
        val testRecord = testRecords[position]
        holder.testName.text = testRecord.testName
        holder.testDate.text = testRecord.testDate
        holder.testDetails.text = testRecord.testDetails
    }

    override fun getItemCount() = testRecords.size
}