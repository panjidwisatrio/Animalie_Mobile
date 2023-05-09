package com.panji.animalie.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.panji.animalie.databinding.ActivityRegisterBinding
import com.panji.animalie.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginLinkPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.DaftarButton.setOnClickListener {
            val name = binding.NamaForm.text.toString()
            val username = binding.UsernameForm.text.toString()
            val email = binding.EmailForm.text.toString()
            val password = binding.NewPasswordForm.text.toString()

            // validasi input
            if (email.isBlank() || password.isBlank() || name.isBlank() || username.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // proses register di sini
            // ...

            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

            // redirect ke halaman login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}