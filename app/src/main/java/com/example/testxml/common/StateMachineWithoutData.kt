package com.example.testxml.common

sealed class StateMachineWithoutData (
    val message: String? = null
) {
    class Success() : StateMachineWithoutData()
    class Error(message: String) : StateMachineWithoutData(message)
    class Loading() : StateMachineWithoutData()
}
