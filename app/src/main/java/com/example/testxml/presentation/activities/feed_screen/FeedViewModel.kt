package com.example.testxml.presentation.activities.feed_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.data.remote.dto.Movy
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.get_movies_use_case.GetMoviesUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FeedViewModel constructor(
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(),
    private val addFavoritesUseCase: AddFavoriteUseCase = AddFavoriteUseCase()
):ViewModel() {
    private val _state = MutableLiveData(MovieStateHandler())
    val state: LiveData<MovieStateHandler> = _state

    val _movies = mutableListOf<Movy>()

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
                            Log.d("penis", "added newItems")
                            Log.d("penis", _movies.map { it.name }.toString())
                        }else{
                            _movies.addAll(curState?.data?.movies ?: listOf())
                        }
                        _state.value = MovieStateHandler(isSuccess = true, movies = curState.data)
                    }
                }
                Log.d("penis",_state.value.toString())
            }
        }
    }

    fun addFavorites(id:String, context: Context){
        viewModelScope.launch {
            addFavoritesUseCase(id,context).collect()
        }
    }
}