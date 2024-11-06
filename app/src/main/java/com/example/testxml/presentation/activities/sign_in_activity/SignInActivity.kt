package com.example.testxml.presentation.activities.sign_in_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.testxml.R
import com.example.testxml.databinding.CoordinatorMainBinding
import com.example.testxml.databinding.SignInActivityBinding
import com.example.testxml.presentation.activities.main_activity.MainActivity
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        val viewModel:SignInViewModel by viewModels()

        val binding = SignInActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val screenHeight = resources.displayMetrics.heightPixels

        val imageBack = binding.imageView3
        imageBack.layoutParams.height = minOf((screenHeight * 0.75).toInt(), imageBack.layoutParams.height)

        val backBtn = binding.BackButton
        val loginBtn = binding.btnLogin
        val login = binding.login
        val password = binding.password


        backBtn.setOnClickListener{
            finish()
        }

        loginBtn.setOnClickListener{
            viewModel.signIn(applicationContext, login.text.toString(), password.text.toString())
        }

        viewModel.state.observe(this){state->
            if (state.isSuccess){
                viewModel.addUser(login.text.toString())
                startActivity(Intent(this, MainActivity::class.java)
                    .putExtra(MainActivity.login, login.text.toString()))
            }
        }

        fun updateButtonState() {
            val isLoginFilled = login.text.toString().isNotEmpty()
            val isPasswordFilled = password.text.toString().isNotEmpty()

            if (isLoginFilled && isPasswordFilled) {
                loginBtn.isEnabled = true
                loginBtn.isClickable = true
                loginBtn.background = resources.getDrawable(R.drawable.orange_gradient)
                loginBtn.setTextColor(resources.getColor(R.color.white))
            } else {
                loginBtn.isEnabled = false
                loginBtn.isClickable = false
                loginBtn.background = resources.getDrawable(R.drawable.edit_shape)
                loginBtn.setTextColor(resources.getColor(R.color.hint_text))
            }
        }

        login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        updateButtonState()
    }
}
