package com.example.testxml.data.remote.dto

import android.util.Log
import com.example.testxml.domain.models.MovieGridCarousel
import com.example.testxml.domain.models.MovieTopCarousel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


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

    val format = DecimalFormat("#,##0.0", DecimalFormatSymbols(Locale.FRANCE))
    val averageRating = this.reviews.sumOf { it.rating }.toFloat() / this.reviews.size

    return MovieGridCarousel(
        poster = this.poster,
        rating = format.format(averageRating).replace(",", ".").toFloat(),
        isFavorite = false
    )
}