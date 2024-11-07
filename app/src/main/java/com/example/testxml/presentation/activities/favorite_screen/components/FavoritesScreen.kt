package com.example.testxml.presentation.activities.favorite_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testxml.R
import com.example.testxml.common.StateMachine
import com.example.testxml.presentation.activities.favorite_screen.FavoritesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    userLogin:String,
    placeholderOnClick:()->Unit,
    movieDetailsOnClick:(String)->Unit
){
    val viewModel:FavoritesViewModel = viewModel{
        FavoritesViewModel(userLogin = userLogin)
    }

    val genresState = viewModel.genres.collectAsState()
    val favoritesFilms = viewModel.movies.collectAsState()

    if(favoritesFilms.value.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = colorResource(id = R.color.orange), modifier = Modifier.fillMaxSize(0.1f))
        }
    }
    else if(genresState.value.isNullOrEmpty() && favoritesFilms.value.value.isNullOrEmpty()){
        FavoritePlaceholder(placeholderOnClick)
    }
    else{
        FavoritesWithContent(viewModel, userLogin, movieDetailsOnClick)
    }
}