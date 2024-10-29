package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.testxml.R
import com.example.testxml.common.font.Manrope
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen (
    id:String
) {
    val viewModel:MovieDetailsViewModel = viewModel {
        MovieDetailsViewModel(id)
    }
    val mainApiState = viewModel.mainState.collectAsState()
    val personState = viewModel.personState.collectAsState()
    val ratingState = viewModel.ratingState.collectAsState()
    val favoriteState = viewModel.favoriteState.collectAsState()

    val lazyState = rememberLazyListState()

    var isTitleVisible by remember{
        mutableStateOf<Boolean>(false)
    }

    LaunchedEffect(lazyState){
        snapshotFlow {lazyState.firstVisibleItemIndex}.collect{
            isTitleVisible = it>0
        }
    }

    /*LaunchedEffect(remember { derivedStateOf { lazyState.firstVisibleItemIndex } }) {
        isTitleVisible = lazyState.firstVisibleItemIndex > 0
    }*/



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mainApiState.value.value?.poster)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier
                            .width(8.dp)
                            .height(16.dp),
                        tint = colorResource(id = R.color.white)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                if(isTitleVisible){
                    Text(
                        text = mainApiState.value.value?.name ?: "",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        color = colorResource(id = R.color.white),
                        maxLines = 2,
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 24.sp,
                        softWrap = true
                    )
                }
            }
            
            IconButton(
                modifier = if(favoriteState.value.value == false) {
                    Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                }
                else{
                    Modifier
                        .size(40.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.red),
                                    colorResource(id = R.color.orange)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                },
                onClick = {
                    if(favoriteState.value.value == false){
                        viewModel.addFavorites(id)
                    }
                    else if(favoriteState.value.value == true){
                        viewModel.deleteFavorites(id)
                    }
                }) {
                Icon(
                    painter = if(favoriteState.value.value == false)
                        painterResource(id = R.drawable.ic_hollow_like)
                    else
                        painterResource(id = R.drawable.ic_fulfilled_like),
                    contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(16.dp),
                    tint = colorResource(id = R.color.white)
                )
            }
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .absolutePadding(top = 120.dp)
                .fillMaxSize(),
            userScrollEnabled = true,
            state = lazyState
        ) {
            item {
                Spacer(modifier = Modifier.height(270.dp))
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
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
                            text = mainApiState.value.value?.name ?: "",
                            fontFamily = Manrope,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white),
                            lineHeight = 40.sp,
                            fontSize = 36.sp,
                        )
                        Text(
                            text = mainApiState.value.value?.tagline?:"",
                            fontFamily = Manrope,
                            fontWeight = FontWeight.Medium,
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
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(text = mainApiState.value.value?.description ?: "",
                        color = colorResource(id = R.color.white),
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item{
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Рейтинг",
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.desc),
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.app_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(20.dp)
                                            .width(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = mainApiState.value.value?.appRating.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_kinopoisk),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = ratingState.value.value?.get(0) ?: "-",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_imdb),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = ratingState.value.value?.get(1) ?: "-",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Информация",
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.desc),
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Страны",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.country.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ContentBlock(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            colorResource(id = R.color.background),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Возраст",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.ageLimit.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Время",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.time.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Год выхода",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.year.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }

                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            item{
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_finance),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Режиссёр",
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.desc),
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        ContentBlock(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    colorResource(id = R.color.background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(personState.value.value)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = mainApiState.value.value?.director.toString(),
                                    fontFamily = Manrope,
                                    fontWeight = FontWeight.Medium,
                                    color = colorResource(id = R.color.white),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_genres),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Жанры",
                                color = colorResource(id = R.color.desc),
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if(mainApiState.value.value != null){
                                for (i in 0..<mainApiState.value.value!!.genres.size){
                                    ContentBlock(
                                        modifier = Modifier
                                            .background(
                                                colorResource(id = R.color.background),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = mainApiState.value.value!!.genres[i].name,
                                            fontFamily = Manrope,
                                            fontWeight = FontWeight.Medium,
                                            color = colorResource(id = R.color.white),
                                            fontSize = 16.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                ContentBlock(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(
                            colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_finance),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Финансы",
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.desc),
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Бюджет",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.budget.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ContentBlock(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorResource(id = R.color.background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Сборы в мире",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.desc),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = mainApiState.value.value?.fees.toString(),
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    MovieDetailsScreen("b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2")
}