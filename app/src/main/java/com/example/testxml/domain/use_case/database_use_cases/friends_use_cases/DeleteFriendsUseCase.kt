package com.example.testxml.domain.use_case.database_use_cases.friends_use_cases

import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.FriendsRepositoryImpl
import com.example.testxml.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFriendsUseCase constructor(
    private val repository: FriendsRepository = FriendsRepositoryImpl()
){
    operator fun invoke(userId:String, friendId:String, avatarLink:String, name:String) : Flow<StateMachineWithoutData> = flow{
        repository.deleteFriend(userId, friendId, avatarLink, name)
    }
}