package com.example.testxml.presentation.activities.profile_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.toProfile
import com.example.testxml.domain.models.Profile
import com.example.testxml.domain.use_case.get_profile_use_case.GetProfileUseCase
import com.example.testxml.domain.use_case.update_profile_use_case.UpdateProfileUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.launch

class ProfileViewModel constructor(
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
    private val updateProfileUseCase: UpdateProfileUseCase = UpdateProfileUseCase()
) : ViewModel() {
    private val _profileState = MutableLiveData(StateHandler<Profile>())
    val profileState: LiveData<StateHandler<Profile>> = _profileState

    private val _updateState = MutableLiveData(StateHandler<Unit>())
    val updateState: LiveData<StateHandler<Unit>> = _updateState

    var updatedAvatarLink:String? = null

    fun getProfile(token:String){
        viewModelScope.launch {
            getProfileUseCase(token).collect{curState->
                _profileState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState.message.toString())
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler<Profile>(isSuccess = true, value = curState.data?.toProfile())
                }
            }
        }
    }

    fun updateProfile(token: String, avatarLink:String){
        viewModelScope.launch{
            profileState.value?.value?.copy(avatarLink = avatarLink)?.let {
                updateProfileUseCase(token, it).collect{curState->
                    when(curState){
                        is StateMachineWithoutData.Error -> _updateState.value = StateHandler(isErrorOccured = true, message = curState.message.toString())
                        is StateMachineWithoutData.Loading -> _updateState.value = StateHandler(isLoading = true)
                        is StateMachineWithoutData.Success -> {
                            updatedAvatarLink = avatarLink
                            _updateState.value = StateHandler(isSuccess = true)
                        }
                    }
                }
            }
        }
    }
}