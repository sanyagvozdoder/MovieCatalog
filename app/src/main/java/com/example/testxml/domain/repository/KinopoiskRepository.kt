package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.MovieListResponseDto
import com.example.testxml.data.remote.dto.PersonResponseDto
import retrofit2.Response

interface KinopoiskRepository {
    suspend fun getPerson(name:String): Response<PersonResponseDto>

    suspend fun getMovieInfo(title:String, year:Int):Response<MovieListResponseDto>
}