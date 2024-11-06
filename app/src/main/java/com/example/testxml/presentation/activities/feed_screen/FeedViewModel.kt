package com.example.testxml.presentation.activities.feed_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.database_use_cases.film_use_cases.AddHiddenFimUseCase
import com.example.testxml.domain.use_case.database_use_cases.film_use_cases.GetHiddenFilmsUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.DeleteGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.GetGenreUseCase
import com.example.testxml.domain.use_case.delete_favorite_use_case.DeleteFavoriteUseCase
import com.example.testxml.domain.use_case.get_movies_use_case.GetMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedViewModel constructor(
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(),
    private val addFavoritesUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val addGenreUseCase: AddGenreUseCase = AddGenreUseCase(),
    private val getGenreUseCase:GetGenreUseCase = GetGenreUseCase(),
    private val deleteGenreUseCase: DeleteGenreUseCase = DeleteGenreUseCase(),
    private val addHiddenFimUseCase: AddHiddenFimUseCase = AddHiddenFimUseCase(),
    private val getHiddenFilmsUseCase: GetHiddenFilmsUseCase = GetHiddenFilmsUseCase(),
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = DeleteFavoriteUseCase(),
):ViewModel() {
    private val _state = MutableLiveData(MovieStateHandler<MoviePageDto>())
    val state: LiveData<MovieStateHandler<MoviePageDto>> = _state

    val _movies = mutableListOf<Movie>()
    var pageCount:Int = 0

    private val _hiddenState = MutableLiveData(StateHandler<List<String>>())
    val hiddenState: LiveData<StateHandler<List<String>>> = _hiddenState

    private val _stateGenre = MutableLiveData(StateHandler<List<String>>())
    val stateGenre: LiveData<StateHandler<List<String>>> = _stateGenre

    var _genres = mutableListOf<String>()

    fun getMovies(page:Int, added:Int = 0){
        viewModelScope.launch {
            getMoviesUseCase(page).collect{curState->
                when(curState){
                    is StateMachine.Error -> _state.value = MovieStateHandler(isErrorOccured = true, message = curState.message ?: "Неизвестная ошибка")
                    is StateMachine.Loading -> _state.value = MovieStateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        pageCount = curState.data?.pageInfo?.pageCount ?: pageCount

                        if(curState.data?.movies != null) {
                            val filteredData = curState.data?.movies?.filterNot { movie ->
                                _hiddenState.value?.value?.any { hiddenId -> movie.id == hiddenId } == true
                            } ?: emptyList()

                            curState.data.movies = filteredData.shuffled()
                        }

                        if(curState.data?.movies?.size!! <=2){
                            _movies += curState?.data?.movies ?: listOf()
                            _state.value = MovieStateHandler(isLoading = true, movies = curState.data)

                            if(curState?.data?.pageInfo?.currentPage!!.plus(1) > curState?.data?.pageInfo?.pageCount!!){
                                _state.value = MovieStateHandler(isSuccess = true, movies = curState.data, added = curState.data?.movies?.size!!)
                            }
                            else{
                                getMovies((curState?.data?.pageInfo?.currentPage ?: 0) + 1, curState.data?.movies?.size!! + added)
                            }
                        }
                        else if (_movies.isNotEmpty()) {
                            _movies += curState.data?.movies ?: listOf()
                            _state.value = MovieStateHandler(isSuccess = true, movies = curState.data, added = curState.data?.movies?.size!! + added)
                        } else {
                            _movies.addAll(curState?.data?.movies ?: listOf())
                            _state.value = MovieStateHandler(isSuccess = true, movies = curState.data, added = curState.data?.movies?.size!! + added)
                        }
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

    fun getGenres(userLogin:String){
        viewModelScope.launch {
            getGenreUseCase(userLogin).collect{ curState ->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> _stateGenre.value = StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        _genres = curState.data?.map{it.genreName}?.toMutableList()!!
                        _stateGenre.value = StateHandler(isSuccess = true, value = _genres)
                    }
                }
            }
        }
    }

    fun deleteGenre(userLogin: String, genreName: String){
        viewModelScope.launch {
            deleteGenreUseCase(userLogin,genreName).collect()
        }
    }

    fun hideFilm(userLogin: String, movieId: String){
        viewModelScope.launch {
            addHiddenFimUseCase(userLogin, movieId).collect()
        }
    }

    fun getHiddenFilms(userLogin: String){
        viewModelScope.launch {
            getHiddenFilmsUseCase(userLogin).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> _hiddenState.value = StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        _hiddenState.value = StateHandler(value = curState.data?.map{it.movieId} , isSuccess = true)
                    }
                }
            }
        }
    }

    fun deleteFavorites(id:String){
        viewModelScope.launch {
            deleteFavoriteUseCase(id).collect()
        }
    }
}