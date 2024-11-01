package com.example.testxml.presentation.activities.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _loginData = MutableLiveData<String>()
    val loginData: LiveData<String> get() = _loginData

    fun setLoginData(value: String) {
        _loginData.value = value
    }
}