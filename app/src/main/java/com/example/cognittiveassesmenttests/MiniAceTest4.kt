package com.example.cognittiveassesmenttests

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cognittiveassesmenttests.helpers.DrawingView

/**
 * This fragment represents the fourth test in the MiniAce series.
 * It includes a drawing view and a clear button to clear the canvas.
 * The layout for this fragment is defined in `R.layout.fragment_mini_ace_test4`.
 */
class MiniAceTest4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mini_ace_test4, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawingView = view.findViewById<DrawingView>(R.id.drawingView)

        val clearButton = view.findViewById<Button>(R.id.clearButton)

        clearButton.setOnClickListener {
            drawingView.clearCanvas()
        }

    }

}