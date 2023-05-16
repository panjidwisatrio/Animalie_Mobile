package com.panji.animalie.ui.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.panji.animalie.R
import com.panji.animalie.ui.chat.ChatActivity
import com.panji.animalie.ui.homepage.HomePage
import com.panji.animalie.ui.interestgroup.InterestGroupActivity
import com.panji.animalie.ui.myprofile.MyProfileActivity
import com.panji.animalie.ui.tag.TagActivity

object BottomNavigationHelper {
    fun setupBottomNavigationBar(
        bottomNavigation: BottomNavigationView,
        context: Context,
        currentActivity: AppCompatActivity
    ) {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            val shouldSwitchActivity =
                when (item.itemId) {
                    R.id.home -> {
                        context.startActivity(Intent(context, HomePage::class.java))
                        true
                    }

                    R.id.interest_group -> {
                        context.startActivity(Intent(context, InterestGroupActivity::class.java))
                        true
                    }

                    R.id.tag -> {
                        context.startActivity(Intent(context, TagActivity::class.java))
                        true
                    }

                    R.id.chat -> {
                        context.startActivity(Intent(context, ChatActivity::class.java))
                        true
                    }

                    R.id.profile -> {
                        context.startActivity(Intent(context, MyProfileActivity::class.java))
                        item.isChecked = true
                        true
                    }

                    else -> false
                }

            if (shouldSwitchActivity) {
                currentActivity.finish()
            }

            item.isChecked = shouldSwitchActivity

            // Set the selected item as checked only if we are switching activities
            shouldSwitchActivity
        }
    }
}