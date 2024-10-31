package com.example.testxml.data.repository

import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.data.remote.MainApiClient
import com.example.testxml.data.remote.MainApiService
import com.example.testxml.data.remote.dto.UserReviewDto
import com.example.testxml.domain.repository.ReviewRepository
import retrofit2.Response

class ReviewRepositoryImpl constructor(
    private val api: MainApiService = MainApiClient.apiService,
    private val token: String = "Bearer " + getFromSharedPrefs(MyApplication.instance)
) : ReviewRepository{
    override suspend fun addReview(movieId: String, userReview: UserReviewDto): Response<Unit> {
        return api.addReview(token, movieId, userReview)
    }

    override suspend fun editReview(
        movieId: String,
        reviewId: String,
        userReview: UserReviewDto
    ): Response<Unit> {
        return api.editReview(token, movieId, reviewId, userReview)
    }

    override suspend fun deleteReview(movieId: String, reviewId: String): Response<Unit> {
        return api.deleteReview(token,movieId, reviewId)
    }
}