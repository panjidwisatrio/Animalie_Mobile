package com.panji.animalie.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.data.preferences.SessionManager
import com.panji.animalie.databinding.ActivityRegisterBinding
import com.panji.animalie.model.response.Auth
import com.panji.animalie.ui.homepage.HomePage
import com.panji.animalie.ui.login.LoginActivity
import com.panji.animalie.util.DialogHelper
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), ViewStateCallback<Auth> {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<ViewModelRegister>()
    private val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAppClosingDialog()
            }
        }

    private val dialogLoading by lazy {
        DialogHelper.showLoadingDialog("Please wait...")
    }

    private val dialogError by lazy {
        DialogHelper.showErrorDialog("Login failed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup exit confirmation
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        DialogHelper.setUpDialog(this@RegisterActivity)

        register()
        toLogin()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.LogoAnimalie, View.TRANSLATION_Y, -100f, 0f).apply {
            duration = 500
            start()
        }

        ObjectAnimator.ofFloat(binding.appName, View.TRANSLATION_Y, -100f, 0f).apply {
            duration = 500
            start()
        }

        val nameForm = ObjectAnimator.ofFloat(binding.NamaForm, View.ALPHA, 1f).setDuration(500)
        val usernameForm = ObjectAnimator.ofFloat(binding.UsernameForm, View.ALPHA, 1f).setDuration(500)
        val emailForm = ObjectAnimator.ofFloat(binding.EmailForm, View.ALPHA, 1f).setDuration(500)
        val newPasswordForm = ObjectAnimator.ofFloat(binding.NewPasswordForm, View.ALPHA, 1f).setDuration(500)
        val confirmPasswordForm = ObjectAnimator.ofFloat(binding.ConfirmPasswordForm, View.ALPHA, 1f).setDuration(500)
        val daftarButton = ObjectAnimator.ofFloat(binding.DaftarButton, View.ALPHA, 1f).setDuration(500)
        val alreadyHaveAccount = ObjectAnimator.ofFloat(binding.Alreadyhaveaccounttxt, View.ALPHA, 1f).setDuration(500)
        val loginLinkPage = ObjectAnimator.ofFloat(binding.LoginLinkPage, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(nameForm, usernameForm, emailForm, newPasswordForm, confirmPasswordForm, daftarButton)
        }

        AnimatorSet().apply {
            playSequentially(together, alreadyHaveAccount, loginLinkPage)
            start()
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

    private fun register() {
        binding.DaftarButton.setOnClickListener {
            val name = binding.NamaForm.text.toString()
            val username = binding.UsernameForm.text.toString()
            val email = binding.EmailForm.text.toString()
            val password = binding.NewPasswordForm.text.toString()
            val confirmPassword = binding.ConfirmPasswordForm.text.toString()

            // validasi input
            if (
                email.isBlank() ||
                password.isBlank() ||
                name.isBlank() ||
                username.isBlank() ||
                confirmPassword.isBlank()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.register(name, username, email, password, confirmPassword).observe(this@RegisterActivity) {
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    }
                }
            }
        }
    }

    private fun toLogin() {
        binding.LoginLinkPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccess(data: Auth) {
        sessionManager.saveId(data.user.id.toString())
        sessionManager.saveToken(data.access_token)

        dialogLoading.dismiss()
        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomePage::class.java))
        finish()
    }

    override fun onLoading() {
        dialogLoading.show()
    }

    override fun onFailed(message: String?) {
        dialogLoading.dismiss()
        dialogError.setMessage(message)
        dialogError.show()
    }
}