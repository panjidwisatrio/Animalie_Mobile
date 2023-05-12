package com.panji.animalie.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.panji.animalie.data.Resource
import com.panji.animalie.databinding.ActivityRegisterBinding
import com.panji.animalie.model.response.Auth
import com.panji.animalie.ui.login.LoginActivity
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), ViewStateCallback<Auth> {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<ViewModelRegister>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVisibility()
        register()
        toLogin()
    }

    private fun register() {
        binding.DaftarButton.setOnClickListener {
            val name = binding.NamaForm.text.toString()
            val username = binding.UsernameForm.text.toString()
            val email = binding.EmailForm.text.toString()
            val password = binding.NewPasswordForm.text.toString()
            val confirmPassword = binding.ConfirmPasswordForm.text.toString()

            // validasi input
            if (email.isBlank() || password.isBlank() || name.isBlank() || username.isBlank() || confirmPassword.isBlank()) {
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

    private fun setVisibility() {
        binding.DaftarButton.visibility = visible
        binding.ConfirmPasswordForm.visibility = visible
        binding.NewPasswordForm.visibility = visible
        binding.EmailForm.visibility = visible
        binding.UsernameForm.visibility = visible
        binding.NamaForm.visibility = visible
        binding.LoginLinkPage.visibility = visible
        binding.progressBarRegister.visibility = invisible
        binding.errorText.visibility = invisible
    }

    private fun toLogin() {
        binding.LoginLinkPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccess(data: Auth) {
        binding.progressBarRegister.visibility = invisible
        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onLoading() {
        binding.DaftarButton.visibility = invisible
        binding.ConfirmPasswordForm.visibility = invisible
        binding.NewPasswordForm.visibility = invisible
        binding.EmailForm.visibility = invisible
        binding.UsernameForm.visibility = invisible
        binding.NamaForm.visibility = invisible
        binding.LoginLinkPage.visibility = invisible
        binding.progressBarRegister.visibility = visible
        binding.errorText.visibility = invisible
    }

    override fun onFailed(message: String?) {
        binding.DaftarButton.visibility = invisible
        binding.ConfirmPasswordForm.visibility = invisible
        binding.NewPasswordForm.visibility = invisible
        binding.EmailForm.visibility = invisible
        binding.UsernameForm.visibility = invisible
        binding.NamaForm.visibility = invisible
        binding.LoginLinkPage.visibility = invisible
        binding.progressBarRegister.visibility = invisible
        binding.errorText.visibility = visible
        binding.errorText.text = message
    }
}