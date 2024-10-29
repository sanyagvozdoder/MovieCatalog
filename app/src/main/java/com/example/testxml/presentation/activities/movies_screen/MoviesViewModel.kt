package com.example.testxml.presentation.activities.movies_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.remote.dto.toMovieGridCarousel
import com.example.testxml.data.remote.dto.toMovieTopCarousel
import com.example.testxml.domain.models.MovieGridCarousel
import com.example.testxml.domain.models.MovieTopCarousel
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.domain.use_case.get_movies_use_case.GetMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import kotlinx.coroutines.launch

class MoviesViewModel constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(),
):ViewModel(){
    private val _favoritesState = MutableLiveData(MovieStateHandler<List<Movie>>())
    val favoriteState: LiveData<MovieStateHandler<List<Movie>>> = _favoritesState

    private val _topState = MutableLiveData(MovieStateHandler<List<MovieTopCarousel>>())
    val topState: LiveData<MovieStateHandler<List<MovieTopCarousel>>> = _topState

    private val _gridState = MutableLiveData(MovieStateHandler<List<MovieGridCarousel>>())
    val gridState: LiveData<MovieStateHandler<List<MovieGridCarousel>>> = _gridState

    fun getFavorites(){
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect{curState->
                _favoritesState.value = when(curState){
                    is StateMachine.Error -> MovieStateHandler(isErrorOccured = true, message = curState?.message ?: "")
                    is StateMachine.Loading -> MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> MovieStateHandler(isSuccess = true, movies = curState?.data)
                }
            }
        }
    }

    fun getMovies(page:Int){
        viewModelScope.launch {
            getMoviesUseCase(page).collect{curState->
                when(curState){
                    is StateMachine.Error -> _gridState.value = MovieStateHandler(isErrorOccured = true, message = curState.message ?: "Неизвестная ошибка")
                    is StateMachine.Loading -> _gridState.value = MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        val listMovies = mutableListOf<MovieGridCarousel>()
                        curState.data?.movies?.forEachIndexed{index, movie ->
                            listMovies.add(movie.toMovieGridCarousel())
                            if(favoriteState.value?.movies?.any { it.id == movie.id } == true){
                                listMovies[index].isFavorite = true
                            }
                        }
                        _gridState.value = MovieStateHandler(isSuccess = true, movies = listMovies)
                    }
                }
            }
        }
    }

    fun getTopMovies(page:Int){
        viewModelScope.launch {
            getMoviesUseCase(page).collect{curState->
                when(curState){
                    is StateMachine.Error -> _topState.value = MovieStateHandler(isErrorOccured = true, message = curState.message ?: "Неизвестная ошибка")
                    is StateMachine.Loading -> _topState.value = MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        val listMovies = mutableListOf<MovieTopCarousel>()
                        curState.data?.movies?.forEachIndexed{index, movie ->
                            if(index<=4){
                                listMovies.add(movie.toMovieTopCarousel())
                            }
                        }
                        _topState.value = MovieStateHandler(isSuccess = true, movies = listMovies)
                    }
                }
            }
        }
    }
}