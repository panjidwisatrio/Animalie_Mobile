package com.panji.animalie.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.ActivityOtherProfileBinding
import com.panji.animalie.model.response.MyProfileResponse
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.Constanta
import com.panji.animalie.util.Constanta.EXTRA_USER
import com.panji.animalie.util.Constanta.TAB_TITLES_OTHER_PROFILE
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtherProfileActivity : AppCompatActivity(), ViewStateCallback<MyProfileResponse> {

    private lateinit var binding: ActivityOtherProfileBinding
    private val viewModel by viewModels<ViewModelMyProfile>()
    private var username: String = ""
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.profileToolbar.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Other Profile"

        username = intent.getStringExtra(EXTRA_USER).toString()

        getProfileData()
    }

    private fun getProfileData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getProfile(username = username).observe(this@OtherProfileActivity) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
    }

    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "otherProfile", "otherProfile", userId = userId)

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_OTHER_PROFILE[position])
            }.attach()
        }
    }

    override fun onSuccess(data: MyProfileResponse) {
        userId = data.user.id.toString()
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
                Glide.with(this@OtherProfileActivity)
                    .load(Constanta.URL_IMAGE + fixString)
                    .into(profilePhoto)
            }
        }

        setTabLayout()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}