package com.panji.animalie.ui.editprofile

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
import com.panji.animalie.data.Resource
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.databinding.ActivityEditProfileBinding
import com.panji.animalie.model.response.EditProfileResponse
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity(), ViewStateCallback<EditProfileResponse> {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel by viewModels<ViewModelEditProfile>()
    private val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }

    private var name = ""
    private var username = ""
    private var work_place: String? = null
    private var job_position: String? = null
    private var email = ""
    private var avatar: String? = null

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
        setPlaceHolderText()

        binding.saveBioButton.setOnClickListener{
            updateProfile()
        }
    }

    private fun updateProfile() {
        val token = sessionManager.fetchToken()

        binding.apply {
            name = editFullName.text.toString()
            username = editUsername.text.toString()
            job_position = editJob.text.toString()
            work_place = editWorkplace.text.toString()
        }

        Log.d("EDP", "$token, $name, $username, $job_position, $work_place, $avatar")

        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.sendEditedProfile(
                    it,
                    name,
                    username,
                    job_position,
                    work_place,
                    email,
                    avatar
                ).observe(this@EditProfileActivity)
                {
                    data -> when(data){
                    is Resource.Error -> onFailed(data.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> data.data?.let { it1 -> onSuccess(it1) }
                    }
                }
            }
        }
    }

    private fun setPlaceHolderText() {
        binding.apply {
            //set profile picture
            val profilePict = intent.getStringExtra("EXTRA_AVATAR")

            if (profilePict == null) {
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            } else {
                Glide.with(this@EditProfileActivity)
                    .load(URL_IMAGE + profilePict)
                    .into(binding.profilePhoto)
            }

            //set form text
            name = intent.getStringExtra("EXTRA_FULLNAME").toString()
            editFullName.setText(name)

            username = intent.getStringExtra("EXTRA_USERNAME").toString()
            editUsername.setText(username)

            job_position = intent.getStringExtra("EXTRA_JOB")
            editJob.setText(job_position)

            work_place = intent.getStringExtra("EXTRA_WORKPLACE")
            editWorkplace.setText(work_place)

            email = intent.getStringExtra("EXTRA_EMAIL").toString()
            editEmail.setText(email)
        }
    }

    override fun onSuccess(data: EditProfileResponse) {
        finish()
    }

    override fun onLoading() {
        //
    }

    override fun onFailed(message: String?) {
        message?.let {
            MaterialAlertDialogBuilder(this@EditProfileActivity)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}