package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.LoginUserDto
import com.example.testxml.data.remote.dto.RegisterUserDto
import com.example.testxml.data.remote.dto.Token
import retrofit2.Response

interface AuthRepository {
    suspend fun registerUser(registerUser: RegisterUserDto): Response<Token>

    suspend fun loginUser(loginUserDto: LoginUserDto): Response<Token>

    suspend fun logoutUser():Response<Unit>
}