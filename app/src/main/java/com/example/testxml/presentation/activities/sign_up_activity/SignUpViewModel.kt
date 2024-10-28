package com.example.testxml.presentation.activities.sign_up_activity

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.RegisterUserDto
import com.example.testxml.domain.use_case.register_user_use_case.RegisterUserUseCase
import com.example.testxml.presentation.activities.sign_up_activity.util.Month
import com.example.testxml.presentation.activities.sign_up_activity.util.Sex
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.launch

class SignUpViewModel constructor(
    val registerUserUseCase: RegisterUserUseCase = RegisterUserUseCase()
):ViewModel() {
    private val _state = MutableLiveData(StateHandler<Unit>())
    val state: LiveData<StateHandler<Unit>> = _state

    fun signUp(context: Context, login: String, password:String, birthDate:String, email:String, gender: Sex, name:String){
        val date = convertDate(birthDate)

        val regDto = RegisterUserDto(
            userName = login,
            password = password,
            birthDate = date,
            email = email,
            gender = gender.ordinal,
            name = name
        )

        Log.d("penis", regDto.birthDate.toString())

        viewModelScope.launch {
            registerUserUseCase(context, regDto).collect{ state->
                _state.value =  when(state){
                    is StateMachineWithoutData.Success -> StateHandler(isSuccess = true)
                    is StateMachineWithoutData.Error -> StateHandler(isErrorOccured = true, message = state.message.toString())
                    is StateMachineWithoutData.Loading -> StateHandler(isLoading = true)
                }
                Log.d("penis",_state.value.toString())
            }
        }
    }

    private fun convertDate(date:String):String{
        val parts = date.split(" ")

        var day = parts[0]
        val year = parts[2]
        val month = Month.convertToString(parts[1])

        if (day.length == 1){
            day = "0$day"
        }

        return "$year-$month-$day"
    }
}