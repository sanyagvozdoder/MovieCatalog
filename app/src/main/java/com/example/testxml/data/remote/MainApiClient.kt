package com.example.testxml.data.remote

import com.example.testxml.data.remote.util.UnauthorizedInterceptor
import com.example.testxml.presentation.components.UnauthorizedEventImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object OkHTTPClient{
    private val interceptor = UnauthorizedInterceptor()

    val okHTTP : OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}

object RetrofitClient{
    private const val BASE_URL = "https://react-midterm.kreosoft.space"

    val retrofit:Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHTTPClient.okHTTP)
            .build()
    }
}

object MainApiClient{
    val apiService : MainApiService by lazy{
        RetrofitClient.retrofit.create(MainApiService::class.java)
    }
}