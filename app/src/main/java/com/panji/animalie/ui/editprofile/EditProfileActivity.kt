package com.panji.animalie.ui.editprofile

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.panji.animalie.R
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.ActivityEditProfileBinding
import com.panji.animalie.model.response.EditProfileResponse
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.DialogHelper
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

    private val dialogLoading by lazy {
        DialogHelper.showLoadingDialog("Please wait...")
    }

    private val dialogError by lazy {
        DialogHelper.showErrorDialog("Failed to update profile")
    }

    private var name = ""
    private var username = ""
    private var workPlace: String? = null
    private var jobPosition: String? = null
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

        binding.saveBioButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        val token = sessionManager.fetchToken()

        binding.apply {
            name = editFullName.text.toString()
            username = editUsername.text.toString()
            jobPosition = editJob.text.toString()
            workPlace = editWorkplace.text.toString()
            email = editEmail.text.toString()
        }

        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.sendEditedProfile(
                    it,
                    name,
                    username,
                    workPlace,
                    jobPosition,
                    email,
                    avatar
                ).observe(this@EditProfileActivity)
                { data ->
                    when (data) {
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
            name = intent.getStringExtra("EXTRA_FULLNAME").toString()
            username = intent.getStringExtra("EXTRA_USERNAME").toString()
            jobPosition = intent.getStringExtra("EXTRA_JOB")
            workPlace = intent.getStringExtra("EXTRA_WORKPLACE")
            email = intent.getStringExtra("EXTRA_EMAIL").toString()
            avatar = intent.getStringExtra("EXTRA_AVATAR")

            if (avatar == null) {
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            } else {
                val stringBuilder = avatar?.let { StringBuilder(it) }
                val fixString = stringBuilder?.replace(11, 12, "/").toString()

                Glide.with(this@EditProfileActivity)
                    .load(URL_IMAGE + fixString)
                    .into(binding.profilePhoto)
            }

            //set form text
            editFullName.setText(name)
            editUsername.setText(username)
            editJob.setText(jobPosition)
            editWorkplace.setText(workPlace)
            editEmail.setText(email)
        }
    }

    override fun onSuccess(data: EditProfileResponse) {
        dialogLoading.dismiss()
        finish()
    }

    override fun onLoading() {
        dialogLoading.show()
    }

    override fun onFailed(message: String?) {
        dialogLoading.dismiss()
        dialogError.show()
        Log.e("EditProfileActivity", message.toString())
    }
}