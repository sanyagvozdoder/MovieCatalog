package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.ProfileDto
import retrofit2.Response

interface ProfileRepository {
    suspend fun getProfileInfo():Response<ProfileDto>

    suspend fun updateProfileInfo(profileDto: ProfileDto):Response<Unit>
}