package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MovieDetailDto
import com.example.testxml.data.remote.dto.MovieListDto
import com.example.testxml.data.remote.dto.MoviePageDto
import retrofit2.Response

interface MoviesRepostitory {
    suspend fun getMovies(page:Int):Response<MoviePageDto>

    suspend fun addFavorite(id:String,token:String):Response<Unit>

    suspend fun getFavoriteMovies(token: String):Response<MovieListDto>

    suspend fun getMovieDetail(id: String):Response<MovieDetailDto>
}