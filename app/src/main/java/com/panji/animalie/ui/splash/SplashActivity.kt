package com.panji.animalie.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panji.animalie.ui.homepage.HomePage
import com.panji.animalie.ui.login.LoginActivity
import com.panji.animalie.util.Constanta.PREFS_NAME

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).apply {
                if (getString("TOKEN", null) != null) {
                    startActivity(
                        Intent(this@SplashActivity, HomePage::class.java)
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(this@SplashActivity, LoginActivity::class.java)
                    )
                    finish()
                }
            }
        }
}