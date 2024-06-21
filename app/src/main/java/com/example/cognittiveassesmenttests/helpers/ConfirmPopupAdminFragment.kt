package com.example.cognittiveassesmenttests.helpers

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.cognittiveassesmenttests.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.auth.FirebaseAuth

/**
 * This class represents a confirmation popup for the admin fragment.
 * It includes functionality for confirming test results and storing them in Firebase.
 */

class ConfirmPopupAdminFragment(
    private val id: String,
    private val userId: String,
    private val spinner1: Spinner,
    private val spinner2: Spinner,
    private val spinner3: Spinner,
    private val spinner4: Spinner,
    private val spinner5: Spinner
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.confirm_popup_admin, null)

            val confirmButton: Button = view.findViewById(R.id.confirmButton)
            confirmButton.setOnClickListener {
                // Get the selected items from the spinners
                val attention = spinner1.selectedItem.toString()
                val memory = spinner2.selectedItem.toString()
                val fluency = spinner3.selectedItem.toString()
                val clockDrawing = spinner4.selectedItem.toString()
                val memoryRecall = spinner5.selectedItem.toString()

                // Store the data in Firebase
                val db = FirebaseFirestore.getInstance()

                val record: Map<String, Any> = hashMapOf(
                    "ATTENTION" to attention,
                    "MEMORY" to memory,
                    "FLUENCY" to fluency,
                    "CLOCK DRAWING" to clockDrawing,
                    "MEMORY RECALL" to memoryRecall
                )

                db.collection("Users").document(userId).collection("TestMA").document(id).update(record)


                dismiss()
            }

            val closeButton: Button = view.findViewById(R.id.closeButton)
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