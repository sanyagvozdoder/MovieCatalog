package com.example.testxml.data.remote.dto

data class MovieKinopoisk(
    val countries: List<Country>,
    val genres: List<GenreName>,
    val imdbId: String,
    val kinopoiskId: Int,
    val nameEn: Any,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingImdb: Double,
    val ratingKinopoisk: Double,
    val type: String,
    val year: Int
)