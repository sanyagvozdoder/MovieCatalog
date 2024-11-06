package com.example.testxml.domain.use_case.database_use_cases.user_use_cases

import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.UserRepositoryImpl
import com.example.testxml.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteUserUseCase constructor(
    private val repository: UserRepository = UserRepositoryImpl()
){
    operator fun invoke(userId:String) : Flow<StateMachineWithoutData> = flow{
        repository.deleteUser(userId)
    }
}