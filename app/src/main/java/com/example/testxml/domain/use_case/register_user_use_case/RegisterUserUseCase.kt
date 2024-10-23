package com.example.testxml.domain.use_case.register_user_use_case

import android.content.Context
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.common.sharedprefs.putInSharedPrefs
import com.example.testxml.data.remote.dto.RegisterUserDto
import com.example.testxml.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterUserUseCase constructor(
    private val repository: AuthRepositoryImpl = AuthRepositoryImpl()
) {
    operator fun invoke(context: Context, registerUserDto: RegisterUserDto) : Flow<StateMachineWithoutData> = flow{
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.registerUser(registerUserDto)
            if(response.isSuccessful){
                val token = response.body()
                putInSharedPrefs(context,"token", token?.token.toString() ?: "")
                emit(StateMachineWithoutData.Success())
            }
            else{
                emit(StateMachineWithoutData.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
        }catch (e: HttpException){
            emit(StateMachineWithoutData.Error(e.message ?: "Неизвестная ошибка"))
        }catch (e: IOException){
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}