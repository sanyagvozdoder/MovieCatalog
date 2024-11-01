package com.example.testxml.presentation.activities.main_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testxml.R
import com.example.testxml.databinding.CoordinatorMainBinding

class MainActivity:AppCompatActivity() {
    companion object{
        const val login = "login"
    }

    private lateinit var mainViewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userLogin = intent.getStringExtra(login)

        val binding = CoordinatorMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        WindowInsetsControllerCompat(window, view).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavBar = binding.navBar
        bottomNavBar.setupWithNavController(navController)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        if (userLogin != null) {
            mainViewModel.setLoginData(userLogin)
        }
    }
}