package com.panji.animalie.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
    }

    override fun onLoading() {
        dialogLoading.show()
    }

    override fun onFailed(message: String?) {
        dialogLoading.dismiss()
        dialogError.show()
    }
}