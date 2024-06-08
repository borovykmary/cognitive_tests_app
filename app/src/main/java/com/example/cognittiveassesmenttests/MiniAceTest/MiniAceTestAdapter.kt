package com.example.cognittiveassesmenttests.MiniAceTest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cognittiveassesmenttests.MiniAceTest1
import com.example.cognittiveassesmenttests.MiniAceTest2
import com.example.cognittiveassesmenttests.MiniAceTest3
import com.example.cognittiveassesmenttests.MiniAceTest4
import com.example.cognittiveassesmenttests.MiniAceTest5

class MiniAceTestAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val totalQuestions = 5

    override fun getItemCount(): Int = totalQuestions

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MiniAceTest1()
            1 -> MiniAceTest2()
            2 -> MiniAceTest3()
            3 -> MiniAceTest4()
            4 -> MiniAceTest5()
            else -> throw IllegalStateException("Invalid position for creating fragment")
        }
    }
}