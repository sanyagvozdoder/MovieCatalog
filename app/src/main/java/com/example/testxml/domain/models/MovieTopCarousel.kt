package com.example.testxml.domain.models

import com.example.testxml.data.remote.dto.Genre

data class MovieTopCarousel(
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
)
