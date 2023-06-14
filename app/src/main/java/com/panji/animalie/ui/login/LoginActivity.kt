package com.panji.animalie.ui.login

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
import com.panji.animalie.databinding.ActivityLoginBinding
import com.panji.animalie.model.response.Auth
import com.panji.animalie.ui.homepage.HomePage
import com.panji.animalie.ui.register.RegisterActivity
import com.panji.animalie.util.DialogHelper
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), ViewStateCallback<Auth> {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<ViewModelLogin>()
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

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup exit confirmation
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        DialogHelper.setUpDialog(this@LoginActivity)

        login()
        toRegisterPage()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.LogoAnimalie, "translationY", -100f, 0f).apply {
            duration = 500
            start()
        }

        ObjectAnimator.ofFloat(binding.appName, "translationY", -100f, 0f).apply {
            duration = 500
            start()
        }

        val userNameEmailForm = ObjectAnimator.ofFloat(binding.UsernameEmailForm, View.ALPHA, 1f).setDuration(500)
        val passForm = ObjectAnimator.ofFloat(binding.NewPasswordForm, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val noAccount = ObjectAnimator.ofFloat(binding.NoAccoundtxt, View.ALPHA, 1f).setDuration(500)
        val registerLink = ObjectAnimator.ofFloat(binding.RegistrasiPageLink, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(userNameEmailForm, passForm, login)
        }

        AnimatorSet().apply {
            playSequentially(together, noAccount, registerLink)
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

    private fun login() {
        binding.loginButton.setOnClickListener {
            val email = binding.UsernameEmailForm.text.toString()
            val password = binding.NewPasswordForm.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.login(email, password).observe(this@LoginActivity) {
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    }
                }
            }
        }
    }

    private fun toRegisterPage() {
        binding.RegistrasiPageLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onSuccess(data: Auth) {
        sessionManager.saveToken(data.access_token)
        sessionManager.saveId(data.user.id.toString())

        dialogLoading.dismiss()
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
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

