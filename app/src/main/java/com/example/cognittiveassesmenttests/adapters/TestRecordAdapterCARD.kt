package com.example.cognittiveassesmenttests.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.CardsDetailsDialogFragment
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.dataClasses.TestRecordCARD

class TestRecordAdapterCARD(private val testRecords: List<TestRecordCARD>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<TestRecordAdapterCARD.TestRecordViewHolder>() {

    class TestRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testDate: TextView = view.findViewById(R.id.time)
        val testDetails: TextView = view.findViewById(R.id.seeDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_record_item_cards, parent, false)
        return TestRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestRecordViewHolder, position: Int) {
        Log.d("RecordsFragment", "Test records: $testRecords")
        val testRecord = testRecords[position]
        holder.testDate.text = testRecord.testDate

        holder.testDetails.setOnClickListener {
            val dialog = CardsDetailsDialogFragment.newInstance(testRecord)
            dialog.show(fragmentManager, "DetailsDialogFragment")
        }
    }

    override fun getItemCount() = testRecords.size
}