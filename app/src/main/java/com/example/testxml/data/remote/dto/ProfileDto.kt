package com.example.testxml.data.remote.dto

import com.example.testxml.domain.models.Profile

data class ProfileDto(
    val avatarLink: String?,
    val birthDate: String,
    val email: String,
    val gender: Int,
    val id: String,
    val name: String,
    val nickName: String
)

fun ProfileDto.toProfile():Profile{
    return Profile(
        avatarLink,
        birthDate,
        email,
        gender,
        id,
        name,
        nickName
    )
}