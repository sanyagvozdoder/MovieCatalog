package com.example.testxml.data.remote.dto

data class UserReviewDto(
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)