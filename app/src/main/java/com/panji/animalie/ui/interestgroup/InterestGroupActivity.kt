package com.panji.animalie.ui.interestgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.databinding.ActivityInterestGroupBinding

class InterestGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterestGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}