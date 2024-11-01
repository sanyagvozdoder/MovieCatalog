package com.example.testxml.presentation.activities.movie_details_screen

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.testxml.presentation.activities.movie_details_screen.screen_components.MovieDetailsScreen

class MovieDetailsActivity:AppCompatActivity() {
    companion object{
        val key = "filmId"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmId = intent.getStringExtra(key)

        setContent {
            MaterialTheme {
                if (filmId != null) {
                    MovieDetailsScreen(filmId)
                }
            }
        }
    }
}