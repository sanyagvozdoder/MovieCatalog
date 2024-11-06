package com.example.testxml.presentation.activities.welcome_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.testxml.R
import com.example.testxml.databinding.CoordinatorMainBinding
import com.example.testxml.databinding.WelcomeActivityBinding
import com.example.testxml.presentation.activities.sign_in_activity.SignInActivity
import com.example.testxml.presentation.activities.sign_up_activity.SignUpActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val binding = WelcomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val loginButton = binding.loginButton
        loginButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        val registerButton = binding.registerButton
        registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}