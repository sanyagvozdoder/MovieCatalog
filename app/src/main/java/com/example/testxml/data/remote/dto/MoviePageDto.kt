package com.example.testxml.data.remote.dto

data class MoviePageDto(
    var movies: List<Movie>,
    val pageInfo: PageInfo
)
