package com.example.testxml.domain.models

import com.example.testxml.data.remote.dto.Genre
import com.example.testxml.data.remote.dto.ReviewDetail

data class MovieDetail(
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
    val year: Int,
    val appRating:Float
)
