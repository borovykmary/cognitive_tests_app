package com.example.cognittiveassesmenttests.helpers

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.R

fun showConfirmPopupMA(buttonId: Int, activity: AppCompatActivity, rootLayoutId: Int, dataMap: HashMap<String, String>) {
    activity.findViewById<ImageView>(buttonId).setOnClickListener {
        // Inflate the confirm_popup layout
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.confirm_popup, null)

        // Create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val popupWindow = PopupWindow(popupView, width, height, true)

        // Create a semi-transparent overlay
        val overlay = View(activity)
        overlay.setBackgroundColor(Color.parseColor("#A6000000")) // Semi-transparent black color

        // Add the overlay to the root layout
        val rootLayout = activity.findViewById<ViewGroup>(rootLayoutId)
        val overlayParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rootLayout.addView(overlay, overlayParams)

        // Remove the overlay when the popup window is dismissed
        popupWindow.setOnDismissListener {
            rootLayout.removeView(overlay)
        }

        // Set up the confirm button
        popupView.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            // Create an intent to start MainActivity
            val intent = Intent(activity, MainActivity::class.java)
            // Start MainActivity
            activity.startActivity(intent)
        }

        // Show the popup window
        popupWindow.showAtLocation(activity.findViewById(rootLayoutId), Gravity.CENTER, 0, 0)
    }
}