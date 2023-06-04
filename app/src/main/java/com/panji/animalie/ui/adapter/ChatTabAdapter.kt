package com.panji.animalie.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.panji.animalie.ui.fragments.contact.ContactFragment
import com.panji.animalie.ui.fragments.messagehistory.MessageHistoryFragment

class ChatTabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactFragment.getInstance()
            1 -> MessageHistoryFragment.getInstance()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}
//