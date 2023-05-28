package com.panji.animalie.ui.tag

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.databinding.ActivityDetailTagBinding
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.Constanta
import com.panji.animalie.util.Constanta.EXTRA_SLUG

class DetailTagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTagBinding
    private var slug: String? = ""

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tagToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tag Name"

//        supportActionBar?.subtitle = "200 post"

        slug = intent.getStringExtra(EXTRA_SLUG)

        //setup tab
        setTabLayout(slug)
    }


    private fun setTabLayout(slug: String?) {

        val pageAdapter = SectionTabAdapter(this, "homepage", "tag", selectedTag = slug)

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(Constanta.TAB_TITLES[position])
            }.attach()
        }
    }
}