package com.example.testxml.presentation.activities.movie_details_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.toMovieDetail
import com.example.testxml.domain.models.MovieDetail
import com.example.testxml.domain.use_case.movie_details_use_case.GetMovieDetailsUseCase
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    val id:String,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase()
):ViewModel() {
    private val _mainState = MutableStateFlow(StateHandler<MovieDetail>())
    val mainState = _mainState.asStateFlow()


    init {
        getMovieDetails(id)
    }
    fun getMovieDetails(id:String){
        viewModelScope.launch {
            getMovieDetailsUseCase(id).collect{curState->
                _mainState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState.data?.toMovieDetail())
                }
            }
        }
    }
}