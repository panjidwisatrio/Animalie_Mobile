package com.panji.animalie.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.databinding.ActivityMyProfileBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.Constanta.TAB_TITLES_PROFILE

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
    }

    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "profile")

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_PROFILE[position])
            }.attach()
        }
    }
}