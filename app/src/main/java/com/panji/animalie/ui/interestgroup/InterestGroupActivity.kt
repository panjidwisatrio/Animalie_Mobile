package com.panji.animalie.ui.interestgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.databinding.ActivityInterestGroupBinding
import com.panji.animalie.ui.utils.AppExitHandler
import com.panji.animalie.ui.utils.BottomNavigationHelper

class InterestGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterestGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setup bottom navigation bar
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation,
            this,
            this
        )
    }

    //    setup exit confirmation
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AppExitHandler.handleBackPress(this)
    }

}