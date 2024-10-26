package com.example.testxml.data.remote.dto

import android.util.Log
import com.example.testxml.domain.models.MovieGridCarousel
import com.example.testxml.domain.models.MovieTopCarousel


data class Movie(
    val country: String,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<Review>,
    val year: Int
)

fun Movie.toMovieTopCarousel(): MovieTopCarousel {
    return MovieTopCarousel(
        genres = this.genres,
        id = this.id,
        name = this.name,
        poster = this.poster
    )
}


fun Movie.toMovieGridCarousel(): MovieGridCarousel {
    return MovieGridCarousel(
        poster = this.poster,
        rating = String.format("%.1f", this.reviews.sumOf { it.rating }.toFloat() / this.reviews.size).toFloat(),
        isFavorite = false
    )
}