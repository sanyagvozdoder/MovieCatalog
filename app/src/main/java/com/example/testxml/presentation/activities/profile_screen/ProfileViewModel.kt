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
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.models.Profile
import com.example.testxml.domain.use_case.database_use_cases.friends_use_cases.GetFriendsUseCase
import com.example.testxml.domain.use_case.get_profile_use_case.GetProfileUseCase
import com.example.testxml.domain.use_case.update_profile_use_case.UpdateProfileUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.activities.sign_up_activity.util.Month
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProfileViewModel constructor(
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
    private val updateProfileUseCase: UpdateProfileUseCase = UpdateProfileUseCase(),
    private val getFriendsUseCase: GetFriendsUseCase = GetFriendsUseCase()
) : ViewModel() {
    private val _profileState = MutableLiveData(StateHandler<Profile>())
    val profileState: LiveData<StateHandler<Profile>> = _profileState

    private val _updateState = MutableLiveData(StateHandler<Unit>())
    val updateState: LiveData<StateHandler<Unit>> = _updateState

    private val _friendsState = MutableLiveData(StateHandler<List<Friend>>())
    val friendsState: LiveData<StateHandler<List<Friend>>> = _friendsState

    var updatedAvatarLink:String? = null
    var regularDate:String? = null

    fun getProfile(){
        viewModelScope.launch {
            getProfileUseCase().collect{curState->
                _profileState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState.message.toString())
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        regularDate = curState.data?.birthDate
                        curState.data?.birthDate = curState.data?.let { castDate(it.birthDate) }.toString()
                        StateHandler(isSuccess = true, value = curState.data?.toProfile())
                    }
                }
            }
        }
    }

    fun updateProfile(avatarLink:String){
        viewModelScope.launch{
            profileState.value?.value?.birthDate = regularDate.toString()
            profileState.value?.value?.copy(avatarLink = avatarLink)?.let {
                updateProfileUseCase(it).collect{curState->
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

    fun getFriends(userLogin:String){
        viewModelScope.launch {
            getFriendsUseCase(userLogin).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> {
                        _friendsState.value = StateHandler(isSuccess = true, value = curState.data)
                    }
                }
            }
        }
    }

    fun castDate(date:String):String{
        val dateTime = LocalDateTime.parse(date)

        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        val formattedDate = dateTime.format(formatter)

        val month = dateTime.monthValue
        val monthName = Month.values()[month-1]

        val finalDate = dateTime.dayOfMonth.toString()+ " " + monthName + " " + dateTime.year.toString()

        return finalDate
    }
}