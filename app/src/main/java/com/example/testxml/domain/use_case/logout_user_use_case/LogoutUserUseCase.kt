package com.example.testxml.domain.use_case.logout_user_use_case

import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LogoutUserUseCase(
    private val repository: AuthRepositoryImpl
) {
    operator fun invoke() : Flow<StateMachineWithoutData> = flow{
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.logoutUser()
            if(response.isSuccessful){
                emit(StateMachineWithoutData.Success())
            }
            else{
                emit(StateMachineWithoutData.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
            emit(StateMachineWithoutData.Success())
        }catch (e: HttpException){
            emit(StateMachineWithoutData.Error(e.message ?: "Неизвестная ошибка"))
        }catch (e: IOException){
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}