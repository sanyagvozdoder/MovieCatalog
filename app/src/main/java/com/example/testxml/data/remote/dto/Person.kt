package com.example.testxml.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Person(
    val kinopoiskId: Int,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val sex: String,
    val webUrl: String
)