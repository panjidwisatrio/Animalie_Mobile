package com.panji.animalie.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.panji.animalie.ui.fragments.LatestFragment
import com.panji.animalie.ui.fragments.PopularFragment
import com.panji.animalie.ui.fragments.UnansweredFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentList = listOf(
        LatestFragment(),
        PopularFragment(),
        UnansweredFragment()

        // Add more fragments as needed
    )

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}
