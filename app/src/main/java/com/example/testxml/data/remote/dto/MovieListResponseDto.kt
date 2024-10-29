package com.example.testxml.data.remote.dto

data class MovieListResponseDto(
    val items: List<MovieKinopoisk>,
    val total: Int,
    val totalPages: Int
)