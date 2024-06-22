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

/**
 * This class is an adapter for displaying CARD test records in a RecyclerView.
 *
 * @property testRecords The list of test records to display.
 * @property fragmentManager The FragmentManager for showing the details dialog.
 */
class TestRecordAdapterCARD(private val testRecords: List<TestRecordCARD>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<TestRecordAdapterCARD.TestRecordViewHolder>() {

    /**
     * This class represents a view holder for a test record item.
     *
     * @property testDate The TextView for the test date.
     * @property testDetails The TextView for the test details button.
     */
    class TestRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testDate: TextView = view.findViewById(R.id.simpleText)
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