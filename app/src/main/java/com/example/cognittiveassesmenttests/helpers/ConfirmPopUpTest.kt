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

/**
 * This file contains a function to show a confirmation popup for a test.
 * The popup includes a submit button that starts MainActivity when clicked.
 */

/**
 * Shows a confirmation popup for a test.
 *
 * @param activity The activity where the popup is shown.
 * @param rootLayoutId The ID of the root layout.
 */
fun showConfirmPopupTest(activity: AppCompatActivity, rootLayoutId: Int) {

    // Inflate the confirm_popup layout
    val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val popupView = inflater.inflate(R.layout.confirm_popup_test, null)

    // Create the popup window
    val width = LinearLayout.LayoutParams.WRAP_CONTENT
    val height = LinearLayout.LayoutParams.WRAP_CONTENT
    val popupWindow = PopupWindow(popupView, width, height, true)

    // Prevent the popup from being dismissed by a touch event outside of it
    popupWindow.isOutsideTouchable = false
    popupWindow.isFocusable = false

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
    popupView.findViewById<Button>(R.id.submitTestButton).setOnClickListener {
        // Create an intent to start MainActivity
        val intent = Intent(activity, MainActivity::class.java)
        // Start MainActivity
        activity.startActivity(intent)
    }

    // Show the popup window
    popupWindow.showAtLocation(activity.findViewById(rootLayoutId), Gravity.CENTER, 0, 0)
}
