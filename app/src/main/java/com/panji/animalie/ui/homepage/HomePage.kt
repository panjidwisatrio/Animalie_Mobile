package com.panji.animalie.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.databinding.HomePageBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES


class HomePage : AppCompatActivity() {

    private lateinit var binding: HomePageBinding

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showAppClosingDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.home

        // setup bottomNavigation
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation.bottomNavigation,
            this,
            this
        )

        // setup exit confirmation
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        // setup tab
        setTabLayout()
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Keluar dari aplikasi?")
            .setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
            .show()
    }


    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "homepage", "dashboard")

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}