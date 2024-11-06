package com.example.testxml.presentation.activities.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.toMovieGridCarousel
import com.example.testxml.domain.models.MovieGridCarousel
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.DeleteGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.GetGenreUseCase
import com.example.testxml.domain.use_case.delete_favorite_use_case.DeleteFavoriteUseCase
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel constructor(
    private val userLogin: String,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val getGenreUseCase: GetGenreUseCase = GetGenreUseCase(),
    private val deleteGenreUseCase: DeleteGenreUseCase = DeleteGenreUseCase()
    ) : ViewModel() {

    private var _genres = MutableStateFlow(listOf<String>())
    val genres = _genres.asStateFlow()

    private var _movies = MutableStateFlow(StateHandler<List<MovieGridCarousel>>())
    val movies  = _movies .asStateFlow()

    init{
        getGenres(userLogin)
        getMovies()
    }
    fun deleteGenre(userLogin: String, genreName: String){
        viewModelScope.launch {
            deleteGenreUseCase(userLogin, genreName).collect()
            getGenres(userLogin)
        }
    }

    fun getGenres(userLogin:String){
        viewModelScope.launch {
            getGenreUseCase(userLogin).collect{ curState ->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> {
                        _genres.value = curState.data?.map{it.genreName}?.toMutableList()!!
                    }
                }
            }
        }
    }

    fun getMovies(){
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect{curState->
                _movies.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true)
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState.data?.map { it.toMovieGridCarousel() })
                }
            }
        }
    }
}