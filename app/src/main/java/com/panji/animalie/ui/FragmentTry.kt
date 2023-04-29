package com.panji.animalie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.databinding.ActivityFragmentTryBinding
import com.panji.animalie.ui.adapter.FragmentAdapter
import com.panji.animalie.ui.fragments.LatestFragment
import com.panji.animalie.ui.fragments.PopularFragment
import com.panji.animalie.ui.fragments.UnansweredFragment

//This code is test code

class FragmentTry : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentTryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentTryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val fragmentAdapter = FragmentAdapter(supportFragmentManager)

        fragmentAdapter.addFragment(LatestFragment(), "Latest")
        fragmentAdapter.addFragment(PopularFragment(), "Popular")
        fragmentAdapter.addFragment(UnansweredFragment(), "Unanswered")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}