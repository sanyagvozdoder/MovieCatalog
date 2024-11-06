package com.example.testxml.domain.use_case.update_profile_use_case

import android.util.Log
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.ProfileDto
import com.example.testxml.data.repository.ProfileRepositoryImpl
import com.example.testxml.domain.models.Profile
import com.example.testxml.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class UpdateProfileUseCase constructor(
    private val repository: ProfileRepository = ProfileRepositoryImpl()
) {
    operator fun invoke(profile: Profile): Flow<StateMachineWithoutData> = flow {
        try {
            emit(StateMachineWithoutData.Loading())
            val response = repository.updateProfileInfo(
                ProfileDto(
                    avatarLink = profile.avatarLink,
                    birthDate = profile.birthDate,
                    name = profile.name,
                    nickName = profile.nickName,
                    gender = profile.gender,
                    email = profile.email,
                    id = profile.id
                )
            )

            response.errorBody()?.string()?.let { Log.d("penis", it) }

            if (response.isSuccessful) {
                emit(StateMachineWithoutData.Success())
            } else {
                emit(
                    StateMachineWithoutData.Error(
                        response.errorBody()?.string() ?: "Неизвестная ошибка"
                    )
                )
            }
        } catch (e: HttpException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Неизвестная ошибка"))
        } catch (e: IOException) {
            emit(StateMachineWithoutData.Error(e.message ?: "Проверьте свое интернет подключение"))
        }
    }
}