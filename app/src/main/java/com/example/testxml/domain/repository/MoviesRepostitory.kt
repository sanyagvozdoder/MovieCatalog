package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.remote.dto.Token
import retrofit2.Response

interface MoviesRepostitory {
    suspend fun getMovies(page:Int):Response<MoviePageDto>

    suspend fun getMovieDetails()

    suspend fun addFavorite(id:String,token:String):Response<Unit>
}