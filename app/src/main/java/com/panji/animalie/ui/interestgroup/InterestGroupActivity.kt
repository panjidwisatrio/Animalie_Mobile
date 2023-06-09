package com.panji.animalie.ui.interestgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityInterestGroupBinding
import com.panji.animalie.ui.fragments.adapter.SectionTabAdapter
import com.panji.animalie.ui.fragments.latest.LatestFragment
import com.panji.animalie.ui.fragments.popular.PopularFragment
import com.panji.animalie.ui.fragments.unanswerd.UnansweredFragment
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.Constanta

class InterestGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterestGroupBinding
    private var chip: String = "cow"

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAppClosingDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.interest_group

        // setup bottomNavigation
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation.bottomNavigation,
            this,
            this
        )

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setCheckedChipId()
        setTabLayout(chip)
        searchPost()
    }

    private fun searchPost() {
        binding.searchBar.searchBarLayout.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    performSearch(newText)
                    return false
                }
            })
        }
    }

    private fun performSearch(query: String?) {
        when (val currentFragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}")) {
            is LatestFragment -> {
                currentFragment.getPostLatest(query)
            }

            is PopularFragment -> {
                currentFragment.getPopularPost(query)
            }

            is UnansweredFragment -> {
                currentFragment.getUnansweredPost(query)
            }
        }
    }

    private fun setCheckedChipId() {
        binding.apply {
            cowChip.setOnClickListener {
                cowChip.isChecked = true
                chip = "cow"
                setTabLayout(chip)
            }
            poultryChip.setOnClickListener {
                poultryChip.isChecked = true
                chip = "poultry"
                setTabLayout(chip)
            }
            goatChip.setOnClickListener {
                goatChip.isChecked = true
                chip = "goat"
                setTabLayout(chip)
            }
            sheepChip.setOnClickListener {
                sheepChip.isChecked = true
                chip = "sheep"
                setTabLayout(chip)
            }
            fishChip.setOnClickListener {
                fishChip.isChecked = true
                chip = "fish"
                setTabLayout(chip)
            }
            otherChip.setOnClickListener {
                otherChip.isChecked = true
                chip = "other"
                setTabLayout(chip)
            }
        }
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

    private fun setTabLayout(chipInterest: String) {
        val pageAdapter = SectionTabAdapter(this, "homepage", "interestGroup", chipInterest = chipInterest)

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(Constanta.TAB_TITLES[position])
            }.attach()
        }
    }
}