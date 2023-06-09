package com.panji.animalie.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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

        DialogHelper.setUpDialog(this@LoginActivity)

        login()
        toRegisterPage()
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
        }
    }

    override fun onSuccess(data: Auth) {
        sessionManager.saveToken(data.access_token)
        sessionManager.saveId(data.user.id.toString())

        dialogLoading.dismiss()
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
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

