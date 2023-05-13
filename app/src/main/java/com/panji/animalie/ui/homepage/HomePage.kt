package com.panji.animalie.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.databinding.HomePageBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.ui.utils.AppExitHandler
import com.panji.animalie.ui.utils.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES

class HomePage : AppCompatActivity() {

    private lateinit var binding: HomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setup bottomNavigation

        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation,
            this,
            this
        )

//        setup tab
        setTabLayout()
    }

//    setup exit confirmation
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AppExitHandler.handleBackPress(this)
    }

    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "homepage")

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}