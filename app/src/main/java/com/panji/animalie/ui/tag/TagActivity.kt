package com.panji.animalie.ui.tag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityTagBinding

class TagActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_tag)
    }
}