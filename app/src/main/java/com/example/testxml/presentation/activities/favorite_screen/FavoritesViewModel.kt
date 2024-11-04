package com.example.testxml.presentation.activities.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.DeleteGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.GetGenreUseCase
import com.example.testxml.domain.use_case.delete_favorite_use_case.DeleteFavoriteUseCase
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel constructor(
    private val userLogin: String,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val addFavoriteMoviesUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val deleteFavoriteMoviesUseCase: DeleteFavoriteUseCase = DeleteFavoriteUseCase(),
    private val addGenreUseCase: AddGenreUseCase = AddGenreUseCase(),
    private val getGenreUseCase: GetGenreUseCase = GetGenreUseCase(),
    private val deleteGenreUseCase: DeleteGenreUseCase = DeleteGenreUseCase()
    ) : ViewModel() {
    private var _genres = MutableStateFlow(listOf<String>())
    val genres = _genres.asStateFlow()

    init{
        getGenres(userLogin)
    }

    fun addGenre(userLogin:String,genreName:String){
        viewModelScope.launch {
            addGenreUseCase(userLogin,genreName).collect()
            getGenres(userLogin)
        }
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
}