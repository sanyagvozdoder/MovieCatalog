package com.example.testxml.domain.use_case.get_profile_use_case

import android.util.Log
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.ProfileDto
import com.example.testxml.data.repository.ProfileRepositoryImpl
import com.example.testxml.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetProfileUseCase constructor(
    private val repository: ProfileRepository = ProfileRepositoryImpl()
) {
    operator fun invoke(token:String) : Flow<StateMachine<ProfileDto>> = flow {
        try {
            emit(StateMachine.Loading())
            val response = repository.getProfileInfo(token)
            Log.d("penis",response.toString())
            if (response.isSuccessful){
                emit(StateMachine.Success(response.body()))
            }
            else{
                emit(StateMachine.Error(response.errorBody()?.string() ?: "Неизвестная ошибка"))
            }
        }catch (e: HttpException) {
            emit(StateMachine.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachine.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}