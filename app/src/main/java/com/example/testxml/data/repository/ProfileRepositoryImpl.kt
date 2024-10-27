package com.example.testxml.data.repository

import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.ProfileDto
import com.example.testxml.domain.repository.ProfileRepository
import retrofit2.Response

class ProfileRepositoryImpl constructor(
    private val api:MainApiService = MainApiClient.apiService
):ProfileRepository {
    override suspend fun getProfileInfo(token: String): Response<ProfileDto> {
        return api.getProfileInfo(token)
    }

    override suspend fun updateProfileInfo(token: String, profileDto: ProfileDto): Response<Unit> {
        return api.updateProfileInfo(token, profileDto)
    }
}