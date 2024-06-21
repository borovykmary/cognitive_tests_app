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
import com.example.cognittiveassesmenttests.SDMTDetailsDialogFragment
import com.example.cognittiveassesmenttests.dataClasses.TestRecordSDMT

/**
 * This class is an adapter for displaying SDMT test records in a RecyclerView.
 *
 * @property testRecords The list of test records to display.
 * @property fragmentManager The FragmentManager for showing the details dialog.
 */
class TestRecordAdapterSDMT(private val testRecords: List<TestRecordSDMT>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<TestRecordAdapterSDMT.TestRecordViewHolder>() {

    /**
     * This class represents a view holder for a test record item.
     *
     * @property testDate The TextView for the test date.
     * @property testDetails The TextView for the test details button.
     */
    class TestRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testDate: TextView = view.findViewById(R.id.time)
        val testDetails: TextView = view.findViewById(R.id.seeDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_record_item_sdmt, parent, false)
        return TestRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestRecordViewHolder, position: Int) {
        Log.d("RecordsFragment", "Test records: $testRecords")
        val testRecord = testRecords[position]
        holder.testDate.text = testRecord.testDate


        holder.testDetails.setOnClickListener {
            val dialog = SDMTDetailsDialogFragment.newInstance(testRecord)
            dialog.show(fragmentManager, "DetailsDialogFragment")
        }
    }


    override fun getItemCount() = testRecords.size
}