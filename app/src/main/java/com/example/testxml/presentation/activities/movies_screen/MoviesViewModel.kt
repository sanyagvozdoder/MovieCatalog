package com.example.testxml.presentation.activities.movies_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.remote.dto.PageInfo
import com.example.testxml.data.remote.dto.toMovieGridCarousel
import com.example.testxml.data.remote.dto.toMovieTopCarousel
import com.example.testxml.domain.models.MovieGridCarousel
import com.example.testxml.domain.models.MovieTopCarousel
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.DeleteGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.GetGenreUseCase
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.domain.use_case.get_movies_use_case.GetMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class MoviesViewModel constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(),
    private val getGenreUseCase: GetGenreUseCase = GetGenreUseCase()
):ViewModel(){
    private val _favoritesState = MutableLiveData(MovieStateHandler<List<Movie>>())
    val favoriteState: LiveData<MovieStateHandler<List<Movie>>> = _favoritesState

    private val _topState = MutableLiveData(MovieStateHandler<List<MovieTopCarousel>>())
    val topState: LiveData<MovieStateHandler<List<MovieTopCarousel>>> = _topState

    private val _gridState = MutableLiveData(MovieStateHandler<List<MovieGridCarousel>>())
    val gridState: LiveData<MovieStateHandler<List<MovieGridCarousel>>> = _gridState

    private val _stateGenre = MutableLiveData(StateHandler<List<String>>())
    val stateGenre: LiveData<StateHandler<List<String>>> = _stateGenre

    private val _randomState = MutableLiveData(StateHandler<Movie>())
    val randomState:LiveData<StateHandler<Movie>> = _randomState

    var previousFilms = mutableListOf<MovieGridCarousel>()

    var _genres = mutableListOf<String>()
    var pageInfo:PageInfo? = null

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
                        val listMovies = previousFilms
                        if(pageInfo == null){
                            pageInfo = curState.data?.pageInfo
                        }

                        curState.data?.movies?.forEachIndexed{index, movie ->
                            val movieGrid = movie.toMovieGridCarousel()

                            if(favoriteState.value?.movies?.any { it.id == movie.id } == true){
                                movieGrid.isFavorite = true
                            }

                            if(index<=4){
                                listMovies.add(movieGrid)
                            }
                            else{
                                previousFilms = mutableListOf()
                                previousFilms.add(movieGrid)
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
                            else{
                                previousFilms.add(movie.toMovieGridCarousel())
                            }
                        }
                        _topState.value = MovieStateHandler(isSuccess = true, movies = listMovies)
                    }
                }
            }
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

    fun randomInt(start:Int, end:Int) : Int{
        return Random.nextInt(start, end + 1)
    }

    fun getRandomMovie(){
        viewModelScope.launch {
            val randomPage = randomInt(1, pageInfo?.pageCount ?: 5)
            val randomFilmIndex = randomInt(0, pageInfo?.pageSize ?: 5)
            getMoviesUseCase(randomPage).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> _randomState.value = StateHandler(
                        isSuccess = true,
                        value = curState.data?.movies?.get(randomInt(0,randomFilmIndex))
                    )
                }
            }
        }
    }
}