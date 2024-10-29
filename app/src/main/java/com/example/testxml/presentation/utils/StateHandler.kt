package com.example.testxml.presentation.utils

data class StateHandler<T>(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val isSuccess:Boolean = false,
    var value:T? = null,
    val message:String = ""
)
