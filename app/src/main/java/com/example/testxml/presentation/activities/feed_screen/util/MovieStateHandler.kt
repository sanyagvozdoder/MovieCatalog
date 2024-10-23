package com.example.testxml.presentation.activities.feed_screen.util

import com.example.testxml.data.remote.dto.MoviePageDto

data class MovieStateHandler<T>(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val movies:T? = null,
    val isSuccess:Boolean = false,
    val message:String = ""
)
