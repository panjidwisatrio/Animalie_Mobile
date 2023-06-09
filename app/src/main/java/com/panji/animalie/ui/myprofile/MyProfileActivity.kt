package com.panji.animalie.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.databinding.ActivityMyProfileBinding
import com.panji.animalie.model.response.MyProfileResponse
import com.panji.animalie.ui.fragments.adapter.SectionTabAdapter
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES_PROFILE
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyProfileActivity : AppCompatActivity(), ViewStateCallback<MyProfileResponse> {

    private lateinit var binding: ActivityMyProfileBinding
    private val viewModel by viewModels<ViewModelMyProfile>()
    private val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }
    private var userId: String = ""

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showAppClosingDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.profile
        setSupportActionBar(binding.profileToolbar.appBar)
        supportActionBar?.title = "My Profile"

        userId = sessionManager.fetchId() as String

        // setup bottomNavigation
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation.bottomNavigation,
            this,
            this
        )

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setTabLayout()
        getProfileData()
    }

    private fun getProfileData() {
        val token = sessionManager.fetchToken()

        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.getProfile(token = it).observe(this@MyProfileActivity) { it1 ->
                    when (it1) {
                        is Resource.Error -> onFailed(it1.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it1.data?.let { it2 -> onSuccess(it2) }
                    }
                }
            }
        }
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Keluar dari aplikasi?")
            .setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
            .show()
    }

    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "profile", "myProfile", userId = userId, token = sessionManager.fetchToken())

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_PROFILE[position])
            }.attach()
        }
    }

    override fun onSuccess(data: MyProfileResponse) {
        val stringBuilder = data.user.avatar?.let { StringBuilder(it) }
        val fixString = stringBuilder?.replace(11, 12, "/").toString()

        binding.apply {
            progressBar.visibility = invisible
            biodataLayout.visibility = visible
            scrollView.visibility = visible

            fullname.text = data.user.name
            username.text = data.user.username

            if (data.user.job_position == null && data.user.work_place == null) {
                tvJob.text = getString(R.string.belum_ada_pekerjaan)
                tvDi.visibility = invisible
                tvWorkplace.visibility = invisible
            } else {
                tvJob.text = data.user.job_position
                tvWorkplace.text = data.user.work_place
            }

            if (data.user.avatar == null) {
                profilePhoto.setImageResource(R.mipmap.profile_photo_round)
            } else {
                Glide.with(this@MyProfileActivity)
                    .load(URL_IMAGE + fixString)
                    .into(profilePhoto)
            }

        }
    }

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible
            biodataLayout.visibility = invisible
            scrollView.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            progressBar.visibility = invisible
            biodataLayout.visibility = invisible
            scrollView.visibility = invisible
        }

        message?.let {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}