package com.example.cognittiveassesmenttests

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterCARD
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterMA
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterSDMT
import com.example.cognittiveassesmenttests.dataClasses.TestRecordCARD
import com.example.cognittiveassesmenttests.dataClasses.TestRecordMA
import com.example.cognittiveassesmenttests.dataClasses.TestRecordSDMT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val testRecords = mutableListOf<TestRecordCARD>()

        if (userId != null) {
            db.collection("Users").document(userId).collection("TestWC").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val testName = "Wisconsin card sort task"
                        val testDate = "todo"
                        val testDetails = "SEE DETAILS"
                        val testScore = document.getString("CorrectAnswers")
                        val testTime = document.getString("Time")
                        val testRecord = TestRecordCARD(testName, testDate, testDetails, testScore!!, testTime!!)
                        testRecords.add(testRecord)
                    }
                    val adapterCARD = TestRecordAdapterCARD(testRecords, childFragmentManager)
                    recyclerViewCARD.adapter = adapterCARD
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents: ", exception)
                }
        }

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
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-01", "SEE DETAILS", "12/23", "2 min"),
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-02", "SEE DETAILS", "10/23", "2 min"),
            TestRecordCARD("Wisconsin Card Sort Task Results", "2023-01-03", "SEE DETAILS", "15/23", "3 min")
        )

        val adapterSDMT = TestRecordAdapterSDMT(dataSetSDMT, childFragmentManager)
        val adapterMA = TestRecordAdapterMA(dataSetMA, childFragmentManager)

        recyclerViewSDMT.adapter = adapterSDMT
        recyclerViewMA.adapter = adapterMA

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

class SDMTDetailsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_sdmt, null)

            val closeButton: Button = view.findViewById(R.id.submitButton)
            closeButton.setOnClickListener {
                dismiss()
            }
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
class CardsDetailsDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(testRecord: TestRecordCARD): CardsDetailsDialogFragment {
            val fragment = CardsDetailsDialogFragment()
            val args = Bundle()
            args.putString("CorrectAnswers", testRecord.testScore)
            args.putString("time", testRecord.testTime)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_cards, null)

            val scoreCARD: TextView = view.findViewById(R.id.totalCards)
            val time: TextView = view.findViewById(R.id.timeCards)

            scoreCARD.text = arguments?.getString("CorrectAnswers")
            time.text = arguments?.getString("time")


            val closeButton: Button = view.findViewById(R.id.submitButton)
            closeButton.setOnClickListener {
                dismiss()
            }
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
class MADetailsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_ma, null)

            val closeButton: Button = view.findViewById(R.id.submitButton)
            closeButton.setOnClickListener {
                dismiss()
            }
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}