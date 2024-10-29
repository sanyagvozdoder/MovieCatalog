package com.example.testxml.domain.use_case.get_favorite_use_case

import android.content.Context
import android.util.Log
import com.example.testxml.common.StateMachine
import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.repository.MoviesRepositoryImpl
import com.example.testxml.domain.repository.MoviesRepostitory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetFavoriteMoviesUseCase constructor(
    private val repository: MoviesRepostitory = MoviesRepositoryImpl()
) {
    operator fun invoke(): Flow<StateMachine<List<Movie>>> = flow {
        try {
            emit(StateMachine.Loading())
            val response = repository.getFavoriteMovies()
            if (response != null) {
                if (response.isSuccessful){
                    emit(StateMachine.Success(response.body()?.movies))
                }
                else{
                    emit(StateMachine.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
                }
            }
        }catch (e: HttpException) {
            emit(StateMachine.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachine.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}