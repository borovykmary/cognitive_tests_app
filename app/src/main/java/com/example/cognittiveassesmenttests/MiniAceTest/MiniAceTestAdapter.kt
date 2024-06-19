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
    private val fragmentList = arrayListOf<Fragment>(
        MiniAceTest1(),
        MiniAceTest2(),
        MiniAceTest3(),
        MiniAceTest4(),
        MiniAceTest5()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getCurrentFragment(position: Int): Fragment = fragmentList[position]
}
