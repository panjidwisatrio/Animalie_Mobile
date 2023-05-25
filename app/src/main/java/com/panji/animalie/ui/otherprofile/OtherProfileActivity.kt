package com.panji.animalie.ui.otherprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityOtherProfileBinding
import com.panji.animalie.model.response.MyProfileResponse
import com.panji.animalie.ui.adapter.SectionTabAdapter
import com.panji.animalie.util.Constanta.TAB_TITLES_PROFILE
import com.panji.animalie.util.ViewStateCallback

class OtherProfileActivity : AppCompatActivity(), ViewStateCallback<MyProfileResponse> {

    private lateinit var binding: ActivityOtherProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProfileData()
        setTabLayout()
    }

    private fun getProfileData() {

    }

    private fun setTabLayout() {
        val pageAdapter = SectionTabAdapter(this, "profile", "myProfile")

        binding.apply {
            viewPager.adapter = pageAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES_PROFILE[position - 1])
            }.attach()
        }
    }

    override fun onSuccess(data: MyProfileResponse) {
        TODO("Not yet implemented")
    }

    override fun onLoading() {
        TODO("Not yet implemented")
    }

    override fun onFailed(message: String?) {
        TODO("Not yet implemented")
    }
}