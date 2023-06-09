package com.panji.animalie.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogHelper {
    private var builder: MaterialAlertDialogBuilder? = null

    fun setUpDialog(context: Context) {
        builder = MaterialAlertDialogBuilder(context)
    }

    fun showSuccessDialog(msg: String): AlertDialog {
        val alertSuccessBuilder = builder!!
            .setTitle("Success")
            .setMessage(msg)
            .setPositiveButton("OK") { _, _ -> }

        return alertSuccessBuilder.create()
    }

    fun showErrorDialog(msg: String): AlertDialog {
        val alertErrorBuilder = builder!!
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("OK") { _, _ -> }

        return alertErrorBuilder.create()
    }

    fun showLoadingDialog(msg: String): AlertDialog {
        val alertLoadingBuilder = builder!!
            .setTitle("Loading")
            .setMessage(msg)

        return alertLoadingBuilder.create()
    }
}