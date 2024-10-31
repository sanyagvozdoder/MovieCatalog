package com.example.testxml.domain.repository

import com.example.testxml.data.remote.dto.UserReviewDto
import retrofit2.Response

interface ReviewRepository {
    suspend fun addReview(movieId:String , userReview: UserReviewDto): Response<Unit>

    suspend fun editReview(movieId:String , reviewId:String, userReview: UserReviewDto): Response<Unit>

    suspend fun deleteReview(movieId:String , reviewId:String): Response<Unit>
}