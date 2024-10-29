package com.example.testxml.data.remote

import com.example.testxml.data.remote.dto.PersonResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KinopoiskApiService {
    @GET("api/v1/persons")
    suspend fun getPerson(@Header("X-API-KEY") token:String, @Query("name") name:String): Response<PersonResponseDto>

}