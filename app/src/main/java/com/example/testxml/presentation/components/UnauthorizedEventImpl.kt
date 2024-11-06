package com.example.testxml.presentation.components

import android.content.Intent
import com.example.testxml.core.MyApplication
import com.example.testxml.domain.interfaces.UnauthorizedEvent
import com.example.testxml.domain.use_case.logout_user_use_case.LogoutUserUseCase
import com.example.testxml.presentation.activities.welcome_activity.WelcomeActivity

class UnauthorizedEventImpl(): UnauthorizedEvent {
    override fun onEvent() {
        val intent = Intent(MyApplication.instance, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        MyApplication.instance.startActivity(intent)
    }
}