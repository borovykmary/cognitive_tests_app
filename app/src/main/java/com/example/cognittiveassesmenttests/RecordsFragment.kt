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
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * This fragment displays the user's test records.
 */
class RecordsFragment : Fragment() {

    /**
     * Called to have the fragment instantiate its user interface view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_records, container, false)

    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewSDMT: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewSDMT)
        val recyclerViewMA: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewMA)
        val recyclerViewCARD: RecyclerView = view.findViewById(R.id.testRecordsRecycleViewCARD)
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val testRecordsCard = mutableListOf<TestRecordCARD>()
        val testRecordsSDMT = mutableListOf<TestRecordSDMT>()
        val testRecordsMA = mutableListOf<TestRecordMA>()

        if (userId != null) {
            db.collection("Users").document(userId).collection("TestWC").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val inputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

                        val inputDateStr = document.getString("Date")
                        val date = inputFormat.parse(inputDateStr)
                        val testDate = outputFormat.format(date)

                        val testScore = document.getString("CorrectAnswers")
                        val testTime = document.getString("Time")
                        val testRecord = TestRecordCARD(testDate!!, testScore!!, testTime!!)
                        testRecordsCard.add(testRecord)
                    }
                    val adapterCARD = TestRecordAdapterCARD(testRecordsCard, childFragmentManager)
                    recyclerViewCARD.adapter = adapterCARD
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents: ", exception)
                }
        }
        if (userId != null) {
            db.collection("Users").document(userId).collection("TestSDMT").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val inputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

                        val inputDateStr = document.getString("Date")
                        val date = inputFormat.parse(inputDateStr)
                        val testDate = outputFormat.format(date)

                        val testScore = document.getString("CorrectPercentage")
                        val testRecord = TestRecordSDMT(testDate!!, testScore!!)
                        testRecordsSDMT.add(testRecord)
                    }
                    val adapterSDMT = TestRecordAdapterSDMT(testRecordsSDMT, childFragmentManager)
                    recyclerViewSDMT.adapter = adapterSDMT
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents: ", exception)
                }
        }
        if (userId != null) {
            db.collection("Users").document(userId).collection("TestMA")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.contains("MEMORY") && document.getString("MEMORY") != null) {
                        val inputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

                        val inputDateStr = document.getString("DateTime")
                        val date = inputFormat.parse(inputDateStr)
                        val testDate = outputFormat.format(date)
                        val testTime = document.getString("Time")

                        val attentionScore = document.getString("ATTENTION")
                        val clockDrawingScore = document.getString("CLOCK DRAWING")
                        val memoryScore = document.getString("MEMORY")
                        val fluencyScore = document.getString("FLUENCY")
                        val memoryRecallScore = document.getString("MEMORY RECALL")

                        val testRecord = TestRecordMA(
                            testDate!!,
                            testTime!!,
                            attentionScore!!,
                            clockDrawingScore!!,
                            memoryScore!!,
                            fluencyScore!!,
                            memoryRecallScore!!
                        )
                        testRecordsMA.add(testRecord)
                    }
                }
                    val adapterMA = TestRecordAdapterMA(testRecordsMA, childFragmentManager)
                    recyclerViewMA.adapter = adapterMA
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents: ", exception)
                }
        }

        recyclerViewSDMT.layoutManager = LinearLayoutManager(context)
        recyclerViewMA.layoutManager = LinearLayoutManager(context)
        recyclerViewCARD.layoutManager = LinearLayoutManager(context)

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

/**
 * This class represents a dialog fragment for the SDMT details.
 */
class SDMTDetailsDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(testRecord: TestRecordSDMT): SDMTDetailsDialogFragment {
            val fragment = SDMTDetailsDialogFragment()
            val args = Bundle()
            args.putString("Date", testRecord.testDate)
            args.putString("CorrectAnswers", testRecord.testScore)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Called to do initial creation of a fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_sdmt, null)

            val scoreSDMT: TextView = view.findViewById(R.id.totalSDMC)
            val date: TextView = view.findViewById(R.id.dateSDMC)

            date.text = "Date: " + arguments?.getString("Date")
            scoreSDMT.text = "Total: " + arguments?.getString("CorrectAnswers") + "/64"

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

/**
 * This class represents a dialog fragment for the Cards details.
 */
class CardsDetailsDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(testRecord: TestRecordCARD): CardsDetailsDialogFragment {
            val fragment = CardsDetailsDialogFragment()
            val args = Bundle()
            args.putString("Date", testRecord.testDate)
            args.putString("CorrectAnswers", testRecord.testScore)
            args.putString("time", testRecord.testTime)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Called to do initial creation of a fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_cards, null)

            val scoreCARD: TextView = view.findViewById(R.id.totalCards)
            val time: TextView = view.findViewById(R.id.timeCards)
            val date: TextView = view.findViewById(R.id.dateCards)

            date.text = "Date: " + arguments?.getString("Date")
            scoreCARD.text = "Total: " + arguments?.getString("CorrectAnswers") + "/64"
            time.text = "Time: " + arguments?.getString("time")


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

/**
 * This class represents a dialog fragment for the MA details.
 */
class MADetailsDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(testRecord: TestRecordMA): MADetailsDialogFragment {
            val fragment = MADetailsDialogFragment()
            val args = Bundle()
            args.putString("DateTime", testRecord.testDate)
            args.putString("ATTENTION", testRecord.attentionScore)
            args.putString("MEMORY", testRecord.memoryScore)
            args.putString("CLOCK DRAWING", testRecord.clockDrawingScore)
            args.putString("FLUENCY", testRecord.fluencyScore)
            args.putString("MEMORY RECALL", testRecord.memoryRecallScore)
            args.putString("Time", testRecord.testTime)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Called to do initial creation of a fragment.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.see_details_popup_ma, null)

            val time: TextView = view.findViewById(R.id.timeMA)
            val date: TextView = view.findViewById(R.id.dateMA)
            val attention: TextView = view.findViewById(R.id.attentionScore)
            val memory: TextView = view.findViewById(R.id.memoryScore)
            val visuospatial: TextView = view.findViewById(R.id.visuospatialScore)
            val fluency: TextView = view.findViewById(R.id.fluencyScore)
            val memoryRecall: TextView = view.findViewById(R.id.memoryRecallScore)

            date.text = "Date: " + arguments?.getString("DateTime")
            time.text = "Time: " + arguments?.getString("Time")
            attention.text = "Attention: " + arguments?.getString("ATTENTION") + "/4"
            memory.text = "Memory: " + arguments?.getString("MEMORY") + "/7"
            visuospatial.text = "Visuospatial: " + arguments?.getString("CLOCK DRAWING") + "/5"
            fluency.text = "Fluency: " + arguments?.getString("FLUENCY") + "/7"
            memoryRecall.text = "Memory Recall: " + arguments?.getString("MEMORY RECALL") + "/7"


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

