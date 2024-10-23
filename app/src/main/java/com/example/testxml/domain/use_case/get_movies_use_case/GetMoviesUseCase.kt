package com.example.testxml.domain.use_case.get_movies_use_case

import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.repository.MoviesRepositoryImpl
import com.example.testxml.domain.repository.MoviesRepostitory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMoviesUseCase constructor(
    private val repository: MoviesRepostitory = MoviesRepositoryImpl()
) {
    operator fun invoke(page:Int) : Flow<StateMachine<MoviePageDto>> = flow {
        try {
            emit(StateMachine.Loading())
            val response = repository.getMovies(page)
            if (response.isSuccessful){
                emit(StateMachine.Success(response.body()))
            }
            else{
                emit(StateMachine.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
        }catch (e: HttpException) {
            emit(StateMachine.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachine.Error(e.message ?: "Проверьте свое интернет подключение"))
        }

    }
}