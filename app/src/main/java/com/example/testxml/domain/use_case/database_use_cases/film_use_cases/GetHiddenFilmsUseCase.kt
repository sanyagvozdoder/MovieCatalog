package com.example.testxml.domain.use_case.database_use_cases.film_use_cases

import com.example.testxml.common.StateMachine
import com.example.testxml.data.repository.HiddenFilmRepositoryImpl
import com.example.testxml.data.room.entities.HiddenFilm
import com.example.testxml.domain.repository.HiddenFilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHiddenFilmsUseCase constructor(
    private val repository: HiddenFilmRepository = HiddenFilmRepositoryImpl()
){
    operator fun invoke(userId:String) : Flow<StateMachine<List<HiddenFilm>>> = flow{
        emit(StateMachine.Loading())
        val result = repository.getHiddenFilmsByUser(userId)
        emit(StateMachine.Success(result))
    }
}