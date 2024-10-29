package com.example.testxml.domain.use_case.get_movie_info_use_case

import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.MovieDetailDto
import com.example.testxml.data.remote.dto.MovieListResponseDto
import com.example.testxml.data.remote.dto.PersonResponseDto
import com.example.testxml.data.repository.KinopoiskRepositoryImpl
import com.example.testxml.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMovieInfoUseCase constructor(
    private val repository: KinopoiskRepository = KinopoiskRepositoryImpl()
) {
    operator fun invoke(name:String,year:Int) : Flow<StateMachine<MovieListResponseDto>> = flow {
        try {
            emit(StateMachine.Loading())
            val response = repository.getMovieInfo(name,year)

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