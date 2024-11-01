package com.example.testxml.domain.use_case.database_use_cases.user_use_cases

import android.util.Log
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.UserRepositoryImpl
import com.example.testxml.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddUserUseCase constructor(
    private val userRepository: UserRepository = UserRepositoryImpl()
){
    operator fun invoke(userId:String) : Flow<StateMachineWithoutData> = flow{
        userRepository.addUser(userId)
    }
}