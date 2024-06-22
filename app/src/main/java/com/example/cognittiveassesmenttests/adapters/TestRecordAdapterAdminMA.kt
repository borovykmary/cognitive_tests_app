package com.example.cognittiveassesmenttests.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.AdminMAPopupFragment
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.dataClasses.TestRecordMAAdmin

/**
 * This class is an adapter for displaying MiniAce test records for admin in a RecyclerView.
 *
 * @property testRecords The list of test records to display.
 * @property fragmentManager The FragmentManager for showing the details dialog.
 */
class TestRecordAdapterAdminMA(private val testRecords: List<TestRecordMAAdmin>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<TestRecordAdapterAdminMA.TestRecordAdminViewHolder>() {

    /**
     * This class represents a view holder for a test record item.
     *
     * @property testDate The TextView for the test date.
     * @property testDetails The TextView for the test details button.
     */
    class TestRecordAdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testDate: TextView = view.findViewById(R.id.simpleText)
        val testDetails: TextView = view.findViewById(R.id.seeDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRecordAdminViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_record_item_ma, parent, false)
        return TestRecordAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestRecordAdminViewHolder, position: Int) {
        Log.d("RecordsFragment", "Test records: $testRecords")
        val testRecord = testRecords[position]
        holder.testDate.text = testRecord.testDate

        holder.testDetails.setOnClickListener {
            val dialog = AdminMAPopupFragment.newInstance(testRecord)
            dialog.show(fragmentManager, "DetailsDialogFragment")
        }
    }

    override fun getItemCount() = testRecords.size
}
