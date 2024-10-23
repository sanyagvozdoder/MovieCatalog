package com.example.testxml.presentation.utils

data class StateHandler(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val isSuccess:Boolean = false,
    val message:String = ""
)
