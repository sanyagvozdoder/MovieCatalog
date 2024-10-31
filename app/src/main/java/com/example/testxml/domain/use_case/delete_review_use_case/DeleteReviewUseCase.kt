package com.example.testxml.domain.use_case.delete_review_use_case

import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.UserReviewDto
import com.example.testxml.data.repository.ReviewRepositoryImpl
import com.example.testxml.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DeleteReviewUseCase constructor(
    private val repository: ReviewRepository = ReviewRepositoryImpl()
) {
    operator fun invoke(movieId: String, reviewId:String): Flow<StateMachineWithoutData> = flow {
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.deleteReview(movieId, reviewId)

            if (response.isSuccessful){
                emit(StateMachineWithoutData.Success())
            }
            else{
                emit(StateMachineWithoutData.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
        }catch (e: HttpException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}