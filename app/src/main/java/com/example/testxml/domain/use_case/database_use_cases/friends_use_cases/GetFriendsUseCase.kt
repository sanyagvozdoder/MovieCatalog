package com.example.testxml.domain.use_case.database_use_cases.friends_use_cases

import com.example.testxml.common.StateMachine
import com.example.testxml.data.repository.FavoriteGenreRepositoryImpl
import com.example.testxml.data.repository.FriendsRepositoryImpl
import com.example.testxml.data.room.entities.FavoriteGenre
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.repository.FavoriteGenreRepository
import com.example.testxml.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFriendsUseCase constructor(
    private val repository: FriendsRepository = FriendsRepositoryImpl()
){
    operator fun invoke(userId:String) : Flow<StateMachine<List<Friend>>> = flow{
        try {
            //emit(StateMachine.Loading())
            val result = repository.getFriendsByUser(userId)
            emit(StateMachine.Success<List<Friend>>(result))
        }catch (e:Exception){
            //emit(StateMachine.Error(e.message?:""))
        }
    }
}