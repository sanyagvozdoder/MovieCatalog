package com.example.testxml.presentation.activities.movies_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import kotlinx.coroutines.launch

class MoviesViewModel constructor(
    val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase()
):ViewModel(){
    private val _favoritesState = MutableLiveData(MovieStateHandler<List<Movie>>())
    val favoriteState: LiveData<MovieStateHandler<List<Movie>>> = _favoritesState

    fun getFavorites(context: Context){
        viewModelScope.launch {
            getFavoriteMoviesUseCase(context).collect{curState->
                _favoritesState.value = when(curState){
                    is StateMachine.Error -> MovieStateHandler(isErrorOccured = true, message = curState?.message ?: "")
                    is StateMachine.Loading -> MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> MovieStateHandler(isSuccess = true, movies = curState?.data)
                }
            }
        }
    }
}