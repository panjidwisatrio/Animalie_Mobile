package com.panji.animalie.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.databinding.ActivityChatBinding
import com.panji.animalie.ui.adapter.ChatTabAdapter


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
    }

    private fun setTabLayout() {
        val pageAdapter = ChatTabAdapter(this)
        val tabTitles = arrayOf("Contact", "Inbox")

        binding.viewPager.adapter = pageAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position < tabTitles.size) {
                tab.text = tabTitles[position]
            }
        }.attach()
    }


}



