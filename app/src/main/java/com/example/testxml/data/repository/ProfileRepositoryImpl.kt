package com.example.testxml.data.repository

import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.ProfileDto
import com.example.testxml.domain.repository.ProfileRepository
import retrofit2.Response

class ProfileRepositoryImpl constructor(
    private val api:MainApiService = MainApiClient.apiService,
    private val token: String = "Bearer " + getFromSharedPrefs(MyApplication.instance)
):ProfileRepository {
    override suspend fun getProfileInfo(): Response<ProfileDto> {
        return api.getProfileInfo(token)
    }

    override suspend fun updateProfileInfo(profileDto: ProfileDto): Response<Unit> {
        return api.updateProfileInfo(token, profileDto)
    }
}