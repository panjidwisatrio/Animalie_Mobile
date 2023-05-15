package com.panji.animalie.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityMyProfileBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.ui.utils.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES_PROFILE

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showAppClosingDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.selectedItemId = R.id.profile

        // setup bottomNavigation
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation,
            this,
            this
        )

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

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
        val pageAdapter = SectionTabAdapter(this, "profile", "myProfile")

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_PROFILE[position])
            }.attach()
        }
    }
}