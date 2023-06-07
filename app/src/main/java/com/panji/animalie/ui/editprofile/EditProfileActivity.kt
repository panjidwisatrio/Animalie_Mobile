package com.panji.animalie.ui.editprofile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set top app bar
        val appBar = binding.topAppBar
        setSupportActionBar(appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appBar.setNavigationOnClickListener {
            finish()
        }

        //retrieve data from previous activity
        binding.apply {
            //set profile picture
            val profilePict = intent.getStringExtra("EXTRA_AVATAR")

            if (profilePict == null) {
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            } else {
                //TODO: retrieve from storage
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            }

            //set form text
            editFullName.setText(intent.getStringExtra("EXTRA_FULLNAME"))
            editUsername.setText(intent.getStringExtra("EXTRA_USERNAME"))
            editJob.setText(intent.getStringExtra("EXTRA_JOB"))
            editWorkplace.setText(intent.getStringExtra("EXTRA_WORKPLACE"))
            editEmail.setText(intent.getStringExtra("EXTRA_EMAIL"))
        }
    }
}