package com.example.testxml.presentation.activities.movie_details_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MovieDetailDto
import com.example.testxml.data.remote.dto.Person
import com.example.testxml.data.remote.dto.PersonResponseDto
import com.example.testxml.data.remote.dto.toMovieDetail
import com.example.testxml.domain.models.MovieDetail
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.delete_favorite_use_case.DeleteFavoriteUseCase
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.domain.use_case.get_movie_info_use_case.GetMovieInfoUseCase
import com.example.testxml.domain.use_case.get_person_use_case.GetPersonUseCase
import com.example.testxml.domain.use_case.movie_details_use_case.GetMovieDetailsUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel constructor(
    val id:String,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase(),
    private val getPersonUseCase: GetPersonUseCase = GetPersonUseCase(),
    private val getMovieInfoUseCase: GetMovieInfoUseCase = GetMovieInfoUseCase(),
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val addFavoritesUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = DeleteFavoriteUseCase()
):ViewModel() {
    private val _mainState = MutableStateFlow(StateHandler<MovieDetail>())
    val mainState = _mainState.asStateFlow()

    private val _personState = MutableStateFlow(StateHandler<String>())
    val personState = _personState.asStateFlow()

    private val _ratingState = MutableStateFlow(StateHandler<List<String>>())
    val ratingState = _ratingState.asStateFlow()

    private val _favoritesState = MutableStateFlow(StateHandler<Boolean>())
    val favoriteState = _favoritesState.asStateFlow()

    init {
        getMovieDetails(id)
        getFavorites()
    }
    fun getMovieDetails(id:String){
        viewModelScope.launch {
            getMovieDetailsUseCase(id).collect{curState->
                when(curState){
                    is StateMachine.Error -> _mainState.value = StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> _mainState.value = StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        _mainState.value = StateHandler(isSuccess = true, value = curState.data?.toMovieDetail())
                        getPerson(curState.data?.director.toString().split(",")[0])
                        curState.data?.let {
                            getMovieInfo(
                                it.name,
                                it.year
                            )
                        }
                    }
                }
            }
        }
    }

    fun getPerson(name:String){
        viewModelScope.launch {
            getPersonUseCase(name).collect{curState->
                _personState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState.data?.items?.get(0)?.posterUrl)
                }
            }
        }
    }

    fun getMovieInfo(title:String,year:Int){
        viewModelScope.launch {
            getMovieInfoUseCase(title,year).collect{curState->
                _ratingState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = listOf(
                        curState.data?.items?.get(0)?.ratingKinopoisk.toString(),curState.data?.items?.get(0)?.ratingImdb.toString() ))
                }
            }
        }
    }

    fun getFavorites(){
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect{curState->
                _favoritesState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState?.data?.any { it.id == id })
                }
            }
        }
    }

    fun addFavorites(id:String){
        viewModelScope.launch {
            addFavoritesUseCase(id).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _favoritesState.value = StateHandler(isSuccess = true, value = true)
                }
            }
        }
    }

    fun deleteFavorites(id:String){
        viewModelScope.launch {
            deleteFavoriteUseCase(id).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _favoritesState.value = StateHandler(isSuccess = true, value = false)
                }
            }
        }
    }
}