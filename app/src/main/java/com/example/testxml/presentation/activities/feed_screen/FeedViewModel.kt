package com.example.testxml.presentation.activities.feed_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.get_movies_use_case.GetMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedViewModel constructor(
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(),
    private val addFavoritesUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val addGenreUseCase: AddGenreUseCase = AddGenreUseCase()
):ViewModel() {
    private val _state = MutableLiveData(MovieStateHandler<MoviePageDto>())
    val state: LiveData<MovieStateHandler<MoviePageDto>> = _state

    val _movies = mutableListOf<Movie>()

    var pageCount:Int = 0

    fun getMovies(page:Int){
        viewModelScope.launch {
            getMoviesUseCase(page).collect{curState->
                when(curState){
                    is StateMachine.Error -> _state.value = MovieStateHandler(isErrorOccured = true, message = curState.message ?: "Неизвестная ошибка")
                    is StateMachine.Loading -> _state.value = MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        pageCount = curState.data?.pageInfo?.pageCount ?: pageCount
                        if(curState.data?.movies != null){
                            curState.data?.movies = curState.data?.movies?.shuffled()!!
                        }
                        if (_movies.isNotEmpty()){
                            _movies += curState.data?.movies ?: listOf()
                        }else{
                            _movies.addAll(curState?.data?.movies ?: listOf())
                        }
                        _state.value = MovieStateHandler(isSuccess = true, movies = curState.data)
                    }
                }
            }
        }
    }

    fun addFavorites(id:String){
        viewModelScope.launch {
            addFavoritesUseCase(id).collect()
        }
    }

    fun addGenre(userLogin:String,genreName:String ){
        viewModelScope.launch {
            addGenreUseCase(userLogin,genreName).collect()
        }
    }
}