package com.example.testxml.presentation.activities.launch_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testxml.R
import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.presentation.activities.main_activity.MainActivity
import com.example.testxml.presentation.activities.welcome_activity.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_activity)

        val token = getFromSharedPrefs(MyApplication.instance)
        val viewModel:LaunchViewModel by viewModels()

        if (token != "") {
            viewModel.getProfile()
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        viewModel.profileState.observe(this){
            if(it.isSuccess){
                it.value?.let { it1 -> viewModel.addUser(it1) }
                startActivity(Intent(this, MainActivity::class.java)
                    .putExtra(MainActivity.login, it.value.toString()))
                finish()
            }
            else if (it.isErrorOccured){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}