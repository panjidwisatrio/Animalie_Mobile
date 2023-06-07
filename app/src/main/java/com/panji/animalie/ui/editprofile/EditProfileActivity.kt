package com.panji.animalie.ui.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityEditProfileBinding
import com.panji.animalie.util.Constanta

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve data from previous activity

        //set form text
        binding.editFullName.setText(intent.getStringExtra("EXTRA_FULLNAME"))
        binding.editUsername.setText(intent.getStringExtra("EXTRA_USERNAME"))
        binding.editJob.setText(intent.getStringExtra("EXTRA_JOB"))
        binding.editWorkplace.setText(intent.getStringExtra("EXTRA_WORKPLACE"))
        binding.editEmail.setText(intent.getStringExtra("EXTRA_EMAIL"))

    }
}