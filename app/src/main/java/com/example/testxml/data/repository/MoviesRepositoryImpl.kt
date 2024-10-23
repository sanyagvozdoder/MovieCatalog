package com.example.testxml.data.repository

import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MovieListDto
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.domain.repository.MoviesRepostitory
import retrofit2.Response

class MoviesRepositoryImpl constructor(
    private val api: MainApiService = MainApiClient.apiService
) : MoviesRepostitory {
    override suspend fun getMovies(page:Int): Response<MoviePageDto> {
        return api.getMovies(page)
    }

    override suspend fun getMovieDetails() {
        TODO("Not yet implemented")
    }

    override suspend fun addFavorite(id: String, token: String): Response<Unit> {
        return api.addFavoriteMovie(id,token)
    }

    override suspend fun getFavoriteMovies(token: String): Response<MovieListDto> {
        return api.getFavoritesMovies(token)
    }
}