package com.example.testxml.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object KinopoiskRetrofitClient{
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHTTPClient.okHTTP)
            .build()
    }
}

object KinopoiskApiClient{
    val apiService : KinopoiskApiService by lazy{
        KinopoiskRetrofitClient.retrofit.create(KinopoiskApiService::class.java)
    }
}