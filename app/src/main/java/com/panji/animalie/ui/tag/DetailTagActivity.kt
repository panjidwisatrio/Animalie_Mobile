package com.panji.animalie.ui.tag

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.databinding.ActivityDetailTagBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.Constanta.EXTRA_SLUG
import com.panji.animalie.util.Constanta.EXTRA_TAG
import com.panji.animalie.util.Constanta.TAB_TITLES

class DetailTagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTagBinding
    private var slug: String? = ""
    private var nameTag: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        slug = intent.getStringExtra(EXTRA_SLUG)
        nameTag = intent.getStringExtra(EXTRA_TAG)

        setSupportActionBar(binding.tagToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = nameTag

        //setup tab
        setTabLayout()
    }


    private fun setTabLayout() {

        val pageAdapter = SectionTabAdapter(this, "homepage", "tag", selectedTag = slug)

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}