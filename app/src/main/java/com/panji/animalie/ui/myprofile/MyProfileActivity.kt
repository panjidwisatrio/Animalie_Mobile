package com.panji.animalie.ui.myprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.panji.animalie.ui.editprofile.EditProfileActivity
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.ui.login.LoginActivity
import com.panji.animalie.ui.setting.SettingActivity
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.Constanta.TAB_TITLES_PROFILE
import com.panji.animalie.util.Constanta.URL_IMAGE
import com.panji.animalie.util.DialogHelper
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

    private val token by lazy {
        sessionManager.fetchToken()
    }

    private var userId: String = ""

    private val dialogLoading by lazy {
        DialogHelper.showLoadingDialog("Please wait...")
    }

    private val dialogError by lazy {
        DialogHelper.showErrorDialog("Failed to logout")
    }

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

        DialogHelper.setUpDialog(this@MyProfileActivity)

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
        certificateManager()
    }

    override fun onResume() {
        super.onResume()

        updateProfileData()
    }

    private fun certificateManager() {
        binding.apply {
            addCertificate.setOnClickListener {
                MaterialAlertDialogBuilder(this@MyProfileActivity).setTitle("Add Certificate")
                    .setMessage("Fitur ini masih dalam tahap pengembangan")
                    .setPositiveButton("Oke") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
            addEditCertificate.setOnClickListener {
                MaterialAlertDialogBuilder(this@MyProfileActivity).setTitle("Edit Certificate")
                    .setMessage("Fitur ini masih dalam tahap pengembangan")
                    .setPositiveButton("Oke") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
            seeAllCertificate.setOnClickListener {
                MaterialAlertDialogBuilder(this@MyProfileActivity).setTitle("See All Certificate")
                    .setMessage("Fitur ini masih dalam tahap pengembangan")
                    .setPositiveButton("Oke") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    private fun getProfileData() {
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

    private fun updateProfileData() {
        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.getProfile(token = it).observe(this@MyProfileActivity) { it1 ->
                    when (it1) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> it1.data?.let { it2 -> onSuccess(it2) }
                    }
                }
            }
        }
    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            token?.let {
                viewModel.logout(token = it).observe(this@MyProfileActivity) { it1 ->
                    when (it1) {
                        is Resource.Error -> {
                            dialogLoading.dismiss()
                            dialogError.show()
                            Log.e("Logout", it1.message.toString())
                        }
                        is Resource.Loading -> {
                            dialogLoading.show()
                        }
                        is Resource.Success -> {
                            dialogLoading.dismiss()
                            sessionManager.clearSession()
                            startActivity(
                                Intent(
                                    this@MyProfileActivity,
                                    LoginActivity::class.java
                                )
                            )
                            Toast.makeText(
                                this@MyProfileActivity,
                                "Logout berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
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
        val pageAdapter = SectionTabAdapter(
            this,
            "profile",
            "myProfile",
            userId = userId,
            token = sessionManager.fetchToken()
        )

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

            editProfileButton.setOnClickListener {
                openEditProfileActivity(data)
            }
        }
    }

    private fun openEditProfileActivity(data: MyProfileResponse) {
        startActivity(
            Intent(this, EditProfileActivity::class.java)
                .putExtra("EXTRA_FULLNAME", data.user.name)
                .putExtra("EXTRA_USERNAME", data.user.username)
                .putExtra("EXTRA_EMAIL", data.user.email)
                .putExtra("EXTRA_JOB", data.user.job_position)
                .putExtra("EXTRA_WORKPLACE", data.user.work_place)
                .putExtra("EXTRA_AVATAR", data.user.avatar)
        )
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(
                    Intent(this@MyProfileActivity, SettingActivity::class.java)
                )
                true
            }

            R.id.logout -> {
                logout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}