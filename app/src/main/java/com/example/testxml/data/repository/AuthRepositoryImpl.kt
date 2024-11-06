package com.example.testxml.data.repository

import com.example.testxml.common.sharedprefs.deleteInSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.LoginUserDto
import com.example.testxml.data.remote.dto.RegisterUserDto
import com.example.testxml.data.remote.dto.Token
import com.example.testxml.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl constructor(
    private val api:MainApiService = MainApiClient.apiService
) : AuthRepository {
    override suspend fun registerUser(registerUser: RegisterUserDto): Response<Token> {
        return api.registerUser(registerUser)
    }

    override suspend fun loginUser(loginUserDto: LoginUserDto): Response<Token> {
        return api.loginUser(loginUserDto)
    }

    override suspend fun logoutUser(): Response<Unit> {
        deleteInSharedPrefs(MyApplication.instance)
        return api.logoutUser()
    }
}