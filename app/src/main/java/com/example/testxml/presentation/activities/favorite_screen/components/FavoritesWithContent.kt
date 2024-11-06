package com.example.testxml.presentation.activities.favorite_screen.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testxml.R
import com.example.testxml.common.font.Manrope
import com.example.testxml.presentation.activities.favorite_screen.FavoritesViewModel
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity

@Composable
fun FavoritesWithContent(
    viewModel:FavoritesViewModel,
    userLogin:String,
    onItemClick:(String)->Unit
){
    val genres = viewModel.genres.collectAsState()
    val movies = viewModel.movies.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - 48.dp

    Column(
        modifier = Modifier.padding(top = 32.dp)
    ){
        Text(
            text = "Избранное",
            modifier = Modifier.padding(horizontal = 24.dp),
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            fontSize = 24.sp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ){
            item{
                if(!genres.value.isNullOrEmpty()){
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Любимые жанры",
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.red),
                                    colorResource(id = R.color.orange)
                                ),
                                tileMode = TileMode.Mirror
                            ),
                        ),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column{
                        for (i in 0..<genres.value.size){
                            GenreItem(genreName = genres.value[i]) {
                                viewModel.deleteGenre(userLogin, genres.value[i])
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            item{
                if(!movies.value.value.isNullOrEmpty()){
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Любимые фильмы",
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.red),
                                    colorResource(id = R.color.orange)
                                ),
                                tileMode = TileMode.Mirror
                            ),
                        ),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column{
                        movies.value.value!!.chunked(3).forEach { movies ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                movies.forEach { movie ->
                                    MovieItem(
                                        movie = movie,
                                        modifier = Modifier
                                            .width((screenWidth - 16.dp)/3)
                                            .clickable {
                                                onItemClick(movie.id)
                                            }
                                    )
                                }

                                repeat(3 - movies.size) {
                                    Spacer(modifier = Modifier.width((screenWidth - 16.dp)/3))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.height(110.dp))
            }
        }
    }
}
