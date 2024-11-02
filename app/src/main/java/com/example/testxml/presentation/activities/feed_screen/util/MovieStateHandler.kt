package com.example.testxml.presentation.activities.feed_screen.util

data class MovieStateHandler<T>(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val movies:T? = null,
    val isSuccess:Boolean = false,
    val message:String = "",
    val added:Int = 0
)
