package com.example.testxml.domain.use_case.database_use_cases.genre_use_cases

import android.util.Log
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.FavoriteGenreRepositoryImpl
import com.example.testxml.data.room.entities.FavoriteGenre
import com.example.testxml.domain.repository.FavoriteGenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGenreUseCase constructor(
    private val genreRepository: FavoriteGenreRepository = FavoriteGenreRepositoryImpl()
){
    operator fun invoke(userId:String) : Flow<StateMachine<List<FavoriteGenre>>> = flow{
        try {
            emit(StateMachine.Loading())
            val result = genreRepository.getFavoriteGenresByUser(userId)
            emit(StateMachine.Success<List<FavoriteGenre>>(result))
        }catch (e:Exception){
            emit(StateMachine.Error(e.message?:""))
        }
    }
}