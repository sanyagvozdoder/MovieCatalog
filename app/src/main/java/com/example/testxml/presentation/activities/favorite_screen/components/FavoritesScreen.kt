package com.example.testxml.presentation.activities.favorite_screen.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testxml.common.StateMachine
import com.example.testxml.presentation.activities.favorite_screen.FavoritesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    userLogin:String,
    placeholderOnClick:()->Unit
){
    val viewModel:FavoritesViewModel = viewModel{
        FavoritesViewModel(userLogin = userLogin)
    }

    val genresState = viewModel.genres.collectAsState()

    if(genresState.value.isNullOrEmpty()){
        FavoritePlaceholder(placeholderOnClick)
    }
    else{

    }
}