package com.example.testxml.data.remote.dto

import com.example.testxml.domain.models.MovieDetail

data class MovieDetailDto(
    val ageLimit: Int,
    val budget: Int,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewDetail>,
    val tagline: String,
    val time: Int,
    val year: Int
)


fun MovieDetailDto.toMovieDetail():MovieDetail{
    return MovieDetail(
        ageLimit = this.ageLimit,
        budget= this.budget,
        country= this.country,
        description= this.description,
        director= this.director,
        fees= this.fees,
        genres= this.genres,
        id= this.id,
        name= this.name,
        poster= this.poster,
        reviews= this.reviews,
        tagline= this.tagline,
        time= this.time,
        year= this.year,
        appRating = String.format("%.1f", this.reviews.sumOf { it.rating }.toFloat() / this.reviews.size).toFloat()
    )
}