package com.example.testxml.data.remote.dto

data class RegisterUserDto(
    val birthDate: String,
    val email: String,
    val gender: Int,
    val name: String,
    val password: String,
    val userName: String
)