package com.panji.animalie.ui.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}