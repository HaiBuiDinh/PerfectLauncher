package com.xxx.thachraucau.launcher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.xxx.thachraucau.launcher.fragment.*

class MainPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        val fragment = when (position) {
            0 -> UtilitiesFragment()
            1 -> AllAppFragment()
            2 -> LibraryFragment()
            else -> null
        }
        return fragment!!
    }
}