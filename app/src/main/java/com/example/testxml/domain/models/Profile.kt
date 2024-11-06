package com.example.testxml.domain.models

data class Profile(
    val avatarLink: String?,
    var birthDate: String,
    val email: String,
    val gender: Int,
    val id: String,
    val name: String,
    val nickName: String
)
