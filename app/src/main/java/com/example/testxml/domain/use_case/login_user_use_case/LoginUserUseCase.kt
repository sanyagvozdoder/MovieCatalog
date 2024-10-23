package com.example.testxml.domain.use_case.login_user_use_case

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.common.sharedprefs.putInSharedPrefs
import com.example.testxml.data.remote.dto.LoginUserDto
import com.example.testxml.data.repository.AuthRepositoryImpl
import com.example.testxml.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUserUseCase constructor(
    private val repository: AuthRepository = AuthRepositoryImpl()
) {
    operator fun invoke(loginUserDto: LoginUserDto, context: Context): Flow<StateMachineWithoutData> = flow {
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.loginUser(loginUserDto)
            if (response.isSuccessful) {
                val token = response.body()
                putInSharedPrefs(context,"token", token?.token.toString() ?: "")
                emit(StateMachineWithoutData.Success())
            } else {
                emit(StateMachineWithoutData.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
        } catch (e: HttpException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Попробуйте позже"))
        } catch (e: IOException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}