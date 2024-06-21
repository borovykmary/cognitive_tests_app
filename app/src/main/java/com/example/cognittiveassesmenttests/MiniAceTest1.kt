package com.example.cognittiveassesmenttests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * This fragment represents the first test in the MiniAce series.
 * The layout for this fragment is defined in `R.layout.fragment_mini_ace_test1`.
 */
class MiniAceTest1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mini_ace_test1, container, false)
    }

}