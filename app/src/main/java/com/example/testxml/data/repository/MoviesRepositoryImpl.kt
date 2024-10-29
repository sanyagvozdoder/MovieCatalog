package com.example.testxml.data.repository

import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MovieDetailDto
import com.example.testxml.data.remote.dto.MovieListDto
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.domain.repository.MoviesRepostitory
import retrofit2.Response

class MoviesRepositoryImpl constructor(
    private val api: MainApiService = MainApiClient.apiService,
    private val token: String = "Bearer " + getFromSharedPrefs(MyApplication.instance)
) : MoviesRepostitory {
    override suspend fun getMovies(page:Int): Response<MoviePageDto> {
        return api.getMovies(page)
    }

    override suspend fun addFavorite(id: String): Response<Unit> {
        return api.addFavoriteMovie(id, token)
    }

    override suspend fun deleteFavorite(id: String): Response<Unit> {
        return api.deleteFavoriteMovie(id, token)
    }

    override suspend fun getFavoriteMovies(): Response<MovieListDto> {
        return api.getFavoritesMovies(token)
    }

    override suspend fun getMovieDetail(id: String): Response<MovieDetailDto> {
        return api.getMovieDetails(id)
    }
}