package com.example.testxml.domain.use_case.delete_favorite_use_case

import android.util.Log
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.MoviesRepositoryImpl
import com.example.testxml.domain.repository.MoviesRepostitory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DeleteFavoriteUseCase constructor(
    private val repository: MoviesRepostitory = MoviesRepositoryImpl()
) {
    operator fun invoke(id:String): Flow<StateMachineWithoutData> = flow {
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.deleteFavorite(id)

            Log.d("checkFavorite", response.toString())

            if (response.isSuccessful) {
                emit(StateMachineWithoutData.Success())
            } else {
                emit(
                    StateMachineWithoutData.Error(
                        response.errorBody()?.string() ?: "Неизвестная ошибка"
                    )
                )
            }
        }catch (e: HttpException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}