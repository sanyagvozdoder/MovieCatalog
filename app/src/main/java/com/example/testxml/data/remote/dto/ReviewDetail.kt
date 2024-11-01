package com.example.testxml.data.remote.dto

data class ReviewDetail(
    val author: Author,
    var createDateTime: String,
    val id: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)