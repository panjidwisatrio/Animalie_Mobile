package com.panji.animalie.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.panji.animalie.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}