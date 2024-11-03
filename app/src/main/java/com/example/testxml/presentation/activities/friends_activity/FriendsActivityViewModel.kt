package com.example.testxml.presentation.activities.friends_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.use_case.database_use_cases.friends_use_cases.DeleteFriendsUseCase
import com.example.testxml.domain.use_case.database_use_cases.friends_use_cases.GetFriendsUseCase
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FriendsActivityViewModel constructor(
    val getFriendsUseCase: GetFriendsUseCase = GetFriendsUseCase(),
    val deleteFriendsUseCase: DeleteFriendsUseCase = DeleteFriendsUseCase()
): ViewModel() {
    private val _friendsState = MutableLiveData(StateHandler<List<Friend>>())
    val friendsState: LiveData<StateHandler<List<Friend>>> = _friendsState

    fun getFriends(userLogin: String){
        viewModelScope.launch {
            getFriendsUseCase(userLogin).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> _friendsState.value = StateHandler(isSuccess = true, value = curState.data)
                }
            }
        }
    }

    fun deleteFriend(userLogin:String, friendId:String, avatarLink:String?, name:String){
        viewModelScope.launch {
            deleteFriendsUseCase(userLogin, friendId, avatarLink, name).collect()
        }
    }
}