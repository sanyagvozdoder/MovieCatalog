package com.example.testxml.common

sealed class StateMachine<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) : StateMachine<T>(data)
    class Error<T>(message: String) : StateMachine<T>(message = message)
    class Loading<T>() : StateMachine<T>()
}