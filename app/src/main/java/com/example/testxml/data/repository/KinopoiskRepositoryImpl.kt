package com.example.testxml.data.repository

import com.example.testxml.data.remote.KinopoiskApiClient
import com.example.testxml.data.remote.KinopoiskApiService
import com.example.testxml.data.remote.dto.PersonResponseDto
import com.example.testxml.domain.repository.KinopoiskRepository
import retrofit2.Response

class KinopoiskRepositoryImpl constructor(
    private val api:KinopoiskApiService = KinopoiskApiClient.apiService
) : KinopoiskRepository {
    private val token = "37c7aafc-2c8f-4abd-8a5a-f203c81d8343"

    override suspend fun getPerson(name: String): Response<PersonResponseDto> {
        return api.getPerson(token,name)
    }
}