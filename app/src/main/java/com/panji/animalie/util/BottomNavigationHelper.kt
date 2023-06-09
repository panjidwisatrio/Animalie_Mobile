package com.panji.animalie.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
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
        bottomNavigation.setOnItemSelectedListener { item ->
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
                        MaterialAlertDialogBuilder(context).setTitle("Chat")
                            .setMessage("Fitur ini masih dalam tahap pengembangan")
                            .setPositiveButton("Oke") { dialog, _ ->
                                dialog.dismiss()
                            }.show()
//                        context.startActivity(Intent(context, ChatActivity::class.java))
                        false
                    }

                    R.id.profile -> {
                        context.startActivity(Intent(context, MyProfileActivity::class.java))
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