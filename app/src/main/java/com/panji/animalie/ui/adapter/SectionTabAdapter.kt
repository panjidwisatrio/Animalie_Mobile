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

class SectionTabAdapter(
    activity: AppCompatActivity,
    private val type: String,
    private val typePost: String,
    private val userId: String? = null,
    private val chipInterest: String? = null,
    private val selectedTag: String? = null,
    private val token: String? = null,
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        if (type == "homepage") {
            when (position) {
                0 -> fragment = LatestFragment.getInstance(typePost, chipInterest, selectedTag)
                1 -> fragment = PopularFragment.getInstance(typePost, chipInterest)
                2 -> fragment = UnansweredFragment.getInstance(typePost, chipInterest)
            }
        } else if (type == "profile") {
            when (position) {
                0 -> fragment = userId?.let { MyPostFragment.getInstance(it) }
                1 -> fragment = userId?.let { DiscussionFragment.getInstance(it) }
                2 -> fragment = token?.let { SavedPostFragment.getInstance(it) }
            }
        }
        return fragment as Fragment
    }
}