package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testxml.R
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen (

) {
    val viewModel:MovieDetailsViewModel = viewModel<MovieDetailsViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_welcome1),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        )

        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                ContentBlock(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.red),
                                    colorResource(id = R.color.orange)
                                )
                            ), shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "1899",
                            color = colorResource(id = R.color.white),
                            fontSize = 36.sp,
                        )
                        Text(
                            text = "What is lost will be found",
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ContentBlock(
                    modifier = Modifier
                        .background(colorResource(id = R.color.dark_input), shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = "Группа европейских мигрантов покидает Лондон на пароходе, " +
                            "чтобы начать новую жизнь в Нью-Йорке. Когда они сталкиваются с другим судном, " +
                            "плывущим по течению в открытом море, их путешествие превращается в кошмар",
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    MovieDetailsScreen()
}