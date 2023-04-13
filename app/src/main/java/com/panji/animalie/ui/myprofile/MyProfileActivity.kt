package com.panji.animalie.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}