package com.panji.animalie.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.panji.animalie.R
import com.panji.animalie.databinding.HomePageBinding
import com.panji.animalie.model.Post
import com.panji.animalie.ui.adapter.FragmentAdapter
import com.panji.animalie.ui.fragments.LatestFragment
import com.panji.animalie.ui.fragments.PopularFragment
import com.panji.animalie.ui.fragments.UnansweredFragment

class HomePage : AppCompatActivity() {

    private lateinit var binding: HomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePageBinding.inflate(layoutInflater)
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

    private fun getData(): List<Post> {
        return listOf(
            Post(
                1,
                1,
                1,
                "This Post Title",
                "this-post-title",
                "Lorem ipsum are Post content, dolor sit amet, consectetur adipiscing elit. Donec vestibulum tellus et ex tristique, non suscipit urna gravida. Sed nec interdum elit. Vestibulum sit amet felis ac tortor viverra ultrices. Pellentesque eget nibh ipsum",
                "01-01-2023",
                "01-01-2023"
            )
        )
    }

}