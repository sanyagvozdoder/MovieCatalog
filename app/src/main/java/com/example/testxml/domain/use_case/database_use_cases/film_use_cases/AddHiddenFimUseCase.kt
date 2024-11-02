package com.example.testxml.domain.use_case.database_use_cases.film_use_cases

import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.FavoriteGenreRepositoryImpl
import com.example.testxml.data.repository.HiddenFilmRepositoryImpl
import com.example.testxml.data.room.entities.FavoriteGenre
import com.example.testxml.domain.repository.FavoriteGenreRepository
import com.example.testxml.domain.repository.HiddenFilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddHiddenFimUseCase constructor(
    private val hiddenFilmRepository: HiddenFilmRepository = HiddenFilmRepositoryImpl()
){
    operator fun invoke(userId:String,movieId:String) : Flow<StateMachineWithoutData> = flow{
        emit(StateMachineWithoutData.Loading())
        hiddenFilmRepository.addHiddenFilm(userId, movieId)
        emit(StateMachineWithoutData.Success())
    }
}