package com.example.testxml.presentation.utils

data class StateHandler<T>(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val isSuccess:Boolean = false,
    val value:T? = null,
    val message:String = ""
)
