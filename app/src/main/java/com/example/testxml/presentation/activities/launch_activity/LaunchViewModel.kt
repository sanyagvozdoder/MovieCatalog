package com.example.testxml.presentation.activities.launch_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.toProfile
import com.example.testxml.domain.models.Profile
import com.example.testxml.domain.use_case.database_use_cases.user_use_cases.AddUserUseCase
import com.example.testxml.domain.use_case.get_profile_use_case.GetProfileUseCase
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LaunchViewModel constructor(
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
    private val addUserUserUseCase: AddUserUseCase = AddUserUseCase()
) : ViewModel() {
    private val _profileState = MutableLiveData(StateHandler<String>())
    val profileState: LiveData<StateHandler<String>> = _profileState

    fun getProfile(){
        viewModelScope.launch {
            getProfileUseCase().collect{curState->
                _profileState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState.message.toString())
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        StateHandler(isSuccess = true, value = curState.data?.nickName)
                    }
                }
            }
        }
    }

    fun addUser(login:String){
        viewModelScope.launch {
            addUserUserUseCase(login).collect()
        }
    }
}