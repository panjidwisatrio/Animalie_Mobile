package com.panji.animalie.ui.utils

import android.app.Activity
import android.widget.Toast

object AppExitHandler {
    private var isExitRequested = false

    fun handleBackPress(activity: Activity) {
        if (isExitRequested) {
            // Close all activities and exit the app
            activity.finishAffinity()
        } else {
            Toast.makeText(activity, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
            isExitRequested = true
        }
    }
}