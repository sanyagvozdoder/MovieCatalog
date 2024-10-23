package com.example.testxml.presentation.activities.sign_in_activity

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.LoginUserDto
import com.example.testxml.domain.use_case.login_user_use_case.LoginUserUseCase
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignInViewModel constructor(
    private val signInUseCase: LoginUserUseCase = LoginUserUseCase()
): ViewModel() {
    private val _state = MutableLiveData(StateHandler())
    val state: LiveData<StateHandler> = _state

    fun signIn(context: Context, login: String, password:String){
        viewModelScope.launch {
            signInUseCase(LoginUserDto(login,password), context).collect{state->
                _state.value =  when(state){
                    is StateMachineWithoutData.Success -> StateHandler(isSuccess = true)
                    is StateMachineWithoutData.Error -> StateHandler(isErrorOccured = true, message = state.message.toString())
                    is StateMachineWithoutData.Loading -> StateHandler(isLoading = true)
                }
                Log.d("penis",_state.value.toString())
            }
        }
    }
}