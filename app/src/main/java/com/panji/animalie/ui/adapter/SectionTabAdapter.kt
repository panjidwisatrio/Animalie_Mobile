package com.panji.animalie.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.panji.animalie.ui.fragments.discussion.DiscussionFragment
import com.panji.animalie.ui.fragments.latest.LatestFragment
import com.panji.animalie.ui.fragments.mypost.MyPostFragment
import com.panji.animalie.ui.fragments.popular.PopularFragment
import com.panji.animalie.ui.fragments.savedpost.SavedPostFragment
import com.panji.animalie.ui.fragments.unanswerd.UnansweredFragment

class SectionTabAdapter(activity: AppCompatActivity, private val type: String, private val typePost: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        if (type == "homepage") {
            when (position) {
                0 -> fragment = LatestFragment.getInstance(typePost)
                1 -> fragment = PopularFragment.getInstance(typePost)
                2 -> fragment = UnansweredFragment.getInstance(typePost)
            }
        } else if (type == "profile") {
            when (position) {
                0 -> fragment = MyPostFragment.getInstance()
                1 -> fragment = DiscussionFragment.getInstance()
                2 -> fragment = SavedPostFragment.getInstance()
            }
        }
        return fragment as Fragment
    }
}