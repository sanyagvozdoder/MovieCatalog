package com.example.testxml.domain.use_case.database_use_cases.genre_use_cases

import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.FavoriteGenreRepositoryImpl
import com.example.testxml.domain.repository.FavoriteGenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddGenreUseCase constructor(
    private val genreRepository: FavoriteGenreRepository = FavoriteGenreRepositoryImpl()
){
    operator fun invoke(userId:String, genreName:String) : Flow<StateMachineWithoutData> = flow{
        genreRepository.addFavoriteGenre(userId, genreName)
    }
}