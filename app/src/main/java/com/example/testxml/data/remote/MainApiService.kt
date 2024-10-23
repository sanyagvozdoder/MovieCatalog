package com.example.testxml.data.remote

import com.example.testxml.data.remote.dto.LoginUserDto
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.remote.dto.RegisterUserDto
import com.example.testxml.data.remote.dto.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @POST("/api/account/register")
    suspend fun registerUser(@Body registerUser: RegisterUserDto): Response<Token>

    @POST("api/account/login")
    suspend fun loginUser(@Body loginUserDto: LoginUserDto): Response<Token>

    @POST("api/account/logout")
    suspend fun logoutUser():Response<Unit>

    @GET("api/movies/{page}")
    suspend fun getMovies(@Path("page") page:Int):Response<MoviePageDto>

    @POST("api/favorites/{id}/add")
    suspend fun addFavoriteMovie(@Path("id") id:String, @Header("Authorization") token:String):Response<Unit>
}