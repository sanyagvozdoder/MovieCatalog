package com.example.testxml.presentation.activities.feed_screen.util

import com.example.testxml.data.remote.dto.MoviePageDto

data class MovieStateHandler(
    val isLoading: Boolean = false,
    val isErrorOccured:Boolean = false,
    val movies:MoviePageDto? = null,
    val isSuccess:Boolean = false,
    val message:String = ""
)
