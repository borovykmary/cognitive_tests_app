package com.example.cognittiveassesmenttests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterCARD
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterMA
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterSDMT
import com.example.cognittiveassesmenttests.dataClasses.TestRecordCARD
import com.example.cognittiveassesmenttests.dataClasses.TestRecordMA
import com.example.cognittiveassesmenttests.dataClasses.TestRecordSDMT

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_records, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewSDMT: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewSDMT)
        val recyclerViewMA: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewMA)
        val recyclerViewCARD: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewCARD)

        recyclerViewSDMT.layoutManager = LinearLayoutManager(context)
        recyclerViewMA.layoutManager = LinearLayoutManager(context)
        recyclerViewCARD.layoutManager = LinearLayoutManager(context)

        val dataSetSDMT = listOf(
            TestRecordSDMT("Symbol Digit Modalities Test Results", "2023-01-01", "SEE DETAILS"),
            TestRecordSDMT("Symbol Digit Modalities Test Results", "2023-01-02", "SEE DETAILS"),
            TestRecordSDMT("Symbol Digit Modalities Test Results", "2023-01-03", "SEE DETAILS")
        )

        val dataSetMA = listOf(
            TestRecordMA("Mini Ace Test Results", "2023-01-01", "SEE DETAILS"),
            TestRecordMA("Mini Ace Test Results", "2023-01-02", "SEE DETAILS"),
            TestRecordMA("Mini Ace Test Results", "2023-01-03", "SEE DETAILS")
        )

        val dataSetCARD = listOf(
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-01", "SEE DETAILS"),
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-02", "SEE DETAILS"),
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-03", "SEE DETAILS")
        )

        val adapterSDMT = TestRecordAdapterSDMT(dataSetSDMT)
        val adapterMA = TestRecordAdapterMA(dataSetMA)
        val adapterCARD = TestRecordAdapterCARD(dataSetCARD)

        recyclerViewSDMT.adapter = adapterSDMT
        recyclerViewMA.adapter = adapterMA
        recyclerViewCARD.adapter = adapterCARD

        view.findViewById<TextView>(R.id.SDMTResultsButton).setOnClickListener {
            recyclerViewSDMT.visibility = View.VISIBLE
            recyclerViewMA.visibility = View.GONE
            recyclerViewCARD.visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.MAResultsButton).setOnClickListener {
            recyclerViewSDMT.visibility = View.GONE
            recyclerViewMA.visibility = View.VISIBLE
            recyclerViewCARD.visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.CARDResultsButton).setOnClickListener {
            recyclerViewSDMT.visibility = View.GONE
            recyclerViewMA.visibility = View.GONE
            recyclerViewCARD.visibility = View.VISIBLE
        }
    }


}