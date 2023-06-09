package com.panji.animalie.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.databinding.HomePageBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.ui.createpost.CreatePostActivity
import com.panji.animalie.ui.fragments.latest.LatestFragment
import com.panji.animalie.ui.fragments.popular.PopularFragment
import com.panji.animalie.ui.fragments.unanswerd.UnansweredFragment
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES


class HomePage : AppCompatActivity() {

    private lateinit var binding: HomePageBinding

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
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
            binding.bottomNavigation.bottomNavigation, this, this
        )

        // setup exit confirmation
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        // setup tab
        setTabLayout()
        setFab()
        searchPost()

        binding.searchBar.bellIcon.setOnClickListener {
            toNotificationPage()
        }
    }

    override fun onResume() {
        super.onResume()

        setTabLayout()
    }

    private fun toNotificationPage() {
        MaterialAlertDialogBuilder(this).setTitle("Notifikasi")
            .setMessage("Fitur ini masih dalam tahap pengembangan")
            .setPositiveButton("Oke") { dialog, _ ->
                dialog.dismiss()
            }.show()
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
        when (val currentFragment =
            supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}")) {
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

    private fun setFab() {
        binding.createFab.createFab.setOnClickListener {
            startActivity(
                Intent(this, CreatePostActivity::class.java)
                    .putExtra("TYPE", "create")
            )
        }
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this).setTitle("Keluar dari aplikasi?")
            .setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }.setPositiveButton("Ya") { _, _ ->
                finish()
            }.show()
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