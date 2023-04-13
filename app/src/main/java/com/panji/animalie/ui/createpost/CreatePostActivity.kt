package com.panji.animalie.ui.createpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}