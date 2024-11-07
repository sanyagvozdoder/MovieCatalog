package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
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
import com.example.testxml.presentation.activities.movie_details_screen.util.ReviewMode
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    id: String,
    userLogin: String,
    onBack: () -> Unit
) {
    val viewModel: MovieDetailsViewModel = viewModel {
        MovieDetailsViewModel(id, userLogin)
    }

    val mainApiState = viewModel.mainState.collectAsState()
    val personState = viewModel.personState.collectAsState()
    val ratingState = viewModel.ratingState.collectAsState()
    val favoriteState = viewModel.favoriteState.collectAsState()
    val profileState = viewModel.profileState.collectAsState()
    val reviews = viewModel.reviews.collectAsState()
    val genres = viewModel.genres.collectAsState()
    val friends = viewModel.friendsState.collectAsState()

    val lazyState = rememberLazyListState()

    var isDialogVisible by remember {
        mutableStateOf(false)
    }

    var currentMode by remember {
        mutableStateOf(ReviewMode.ADD)
    }

    var isTitleVisible by remember {
        mutableStateOf<Boolean>(false)
    }
    var currentIndex by remember {
        mutableStateOf(0)
    }

    var isUserReviewExists by remember {
        mutableStateOf(false)
    }

    if (profileState.value.isSuccess && mainApiState.value.isSuccess) {
        isUserReviewExists = reviews.value?.any {
            it?.author?.userId == profileState.value.value
        } == true

        if (isUserReviewExists) {
            currentMode = ReviewMode.EDIT
        } else {
            currentMode = ReviewMode.ADD
        }
    }

    LaunchedEffect(lazyState) {
        snapshotFlow { lazyState.firstVisibleItemIndex }.collect {
            isTitleVisible = it > 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .safeDrawingPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(
                    RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mainApiState.value.value?.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                colorResource(id = R.color.gray_alpha),
                                colorResource(id = R.color.gray_alpha_non)
                            )
                        )
                    )
            )
        }


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
                    onClick = onBack
                ) {
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
                if (isTitleVisible) {
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
                modifier = if (favoriteState.value.value == false) {
                    Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.dark_input),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                } else {
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
                    if (favoriteState.value.value == false) {
                        viewModel.addFavorites(id)
                    } else if (favoriteState.value.value == true) {
                        viewModel.deleteFavorites(id)
                    }
                }) {
                Icon(
                    painter = if (favoriteState.value.value == false)
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
                            text = mainApiState.value.value?.tagline ?: "",
                            fontFamily = Manrope,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (friends.value.size != 0) {
                item {
                    var itemsCount by remember{
                        mutableStateOf(0)
                    }
                    ContentBlock(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(
                                colorResource(id = R.color.dark_input),
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row{
                                for (i in 0..2) {
                                    if (i < friends.value.size) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(friends.value[i].avatarLink)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            error = painterResource(
                                                id = R.drawable.blank_avatar
                                            ),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .offset(x = (-8 * i).dp)
                                                .clip(CircleShape)
                                        )
                                        itemsCount = i
                                    }
                                }
                            }
                            Text(
                                text = if (friends.value.size != 1) "нравится ${friends.value.size} вашим друзьям" else "нравится 1 вашему другу",
                                color = colorResource(id = R.color.white),
                                fontFamily = Manrope,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp).offset(x = itemsCount * -8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
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
                    Text(
                        text = mainApiState.value.value?.description ?: "",
                        color = colorResource(id = R.color.white),
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                DefaultBlock(painter = painterResource(id = R.drawable.ic_star), text = "Рейтинг") {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ContentBlock(
                            modifier = Modifier
                                .weight(0.3f)
                                .background(
                                    colorResource(id = R.color.background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
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
                                .weight(0.2f)
                                .background(
                                    colorResource(id = R.color.background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
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
                                .weight(0.2f)
                                .background(
                                    colorResource(id = R.color.background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
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

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                DefaultBlock(
                    painter = painterResource(id = R.drawable.ic_info),
                    text = "Информация"
                ) {
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

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                DefaultBlock(
                    painter = painterResource(id = R.drawable.ic_finance),
                    text = "Режиссёр"
                ) {
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
                                error = painterResource(
                                    id = R.drawable.blank_avatar
                                ),
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

                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                DefaultBlock(painter = painterResource(id = R.drawable.ic_genres), text = "Жанры") {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (mainApiState.value.value != null) {
                            for (i in 0..<mainApiState.value.value!!.genres.size) {
                                var isSelected =
                                    genres.value.any { it == mainApiState.value.value!!.genres[i].name }

                                val modifier = if (!isSelected) {
                                    Modifier
                                        .background(
                                            colorResource(id = R.color.background),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                } else {
                                    Modifier
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    colorResource(id = R.color.red),
                                                    colorResource(id = R.color.orange)
                                                )
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                }

                                ContentBlock(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            if (!isSelected) {
                                                viewModel.addGenre(
                                                    mainApiState.value.value!!.genres[i].name
                                                )
                                            } else {
                                                viewModel.deleteGenre(
                                                    mainApiState.value.value!!.genres[i].name
                                                )
                                            }
                                        }
                                        .then(modifier)
                                ) {
                                    Text(
                                        text = mainApiState.value.value!!.genres[i].name,
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 8.dp
                                        ),
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                DefaultBlock(
                    painter = painterResource(id = R.drawable.ic_finance),
                    text = "Финансы"
                ) {
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

                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                DefaultBlock(
                    painter = painterResource(id = R.drawable.ic_review),
                    text = "Отзывы"
                ) {

                    Column {
                        ContentBlock(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    colorResource(id = R.color.background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(
                                                    if (reviews.value?.get(currentIndex)?.isAnonymous == false || reviews.value?.get(
                                                            currentIndex
                                                        )?.author?.userId == profileState.value.value
                                                    ) {
                                                        reviews.value?.get(
                                                            currentIndex
                                                        )?.author?.avatar
                                                    } else {
                                                        null
                                                    }
                                                )
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            error = painterResource(
                                                id = R.drawable.blank_avatar
                                            ),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .clickable(
                                                    enabled = if (reviews.value?.get(currentIndex)?.isAnonymous == false
                                                        &&
                                                        reviews.value.get(currentIndex)?.author?.userId != profileState.value.value
                                                    )
                                                        true else false,
                                                    onClick = {
                                                        viewModel.addFriend(
                                                            userLogin,
                                                            reviews.value.get(currentIndex)?.author?.userId!!,
                                                            reviews.value.get(currentIndex)?.author?.avatar,
                                                            reviews.value.get(currentIndex)?.author?.nickName!!
                                                        )
                                                    }

                                                ),
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = if (reviews.value?.get(currentIndex)?.isAnonymous == false || reviews.value?.get(
                                                        currentIndex
                                                    )?.author?.userId == profileState.value.value
                                                ) {
                                                    reviews.value?.get(
                                                        currentIndex
                                                    )?.author?.nickName.toString()
                                                } else {
                                                    "Анонимный пользователь"
                                                },
                                                fontFamily = Manrope,
                                                fontWeight = FontWeight.Medium,
                                                color = colorResource(id = R.color.white),
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = reviews.value?.get(
                                                    currentIndex
                                                )?.createDateTime.toString(),
                                                fontFamily = Manrope,
                                                fontWeight = FontWeight.Medium,
                                                color = colorResource(id = R.color.hint_text),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                    ContentBlock(
                                        modifier = Modifier
                                            .background(
                                                color = Color(
                                                    (255 - ((reviews.value?.get(
                                                        currentIndex
                                                    )?.rating?.times(
                                                        20
                                                    ))?.toInt() ?: 0)),
                                                    (reviews.value?.get(
                                                        currentIndex
                                                    )?.rating?.times(
                                                        20
                                                    ))?.toInt() ?: 0,
                                                    0
                                                ),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_star),
                                                contentDescription = null,
                                                tint = colorResource(id = R.color.white),
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text(
                                                text = reviews.value?.get(currentIndex)?.rating.toString(),
                                                fontFamily = Manrope,
                                                fontWeight = FontWeight.Medium,
                                                color = colorResource(id = R.color.white),
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = reviews.value?.get(currentIndex)?.reviewText.toString(),
                                    fontFamily = Manrope,
                                    fontWeight = FontWeight.Medium,
                                    color = colorResource(id = R.color.desc),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (
                                (isUserReviewExists && reviews.value?.get(currentIndex)?.author?.userId == profileState.value.value)
                                ||
                                (!isUserReviewExists)
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    colorResource(id = R.color.red),
                                                    colorResource(id = R.color.orange)
                                                )
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .height(40.dp)
                                        .padding(horizontal = 10.dp),
                                    onClick = {
                                        isDialogVisible = true
                                    }
                                ) {
                                    Text(
                                        text =
                                        if (reviews.value?.get(currentIndex)?.author?.userId != profileState.value.value)
                                            "Добавить отзыв"
                                        else
                                            "Изменить отзыв",
                                        fontFamily = Manrope,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.white),
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            if (isUserReviewExists && reviews.value?.get(currentIndex)?.author?.userId == profileState.value.value) {
                                IconButton(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = colorResource(id = R.color.background),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 24.dp,
                                                bottomEnd = 24.dp
                                            )
                                        ),
                                    enabled = currentIndex != 0,
                                    onClick = {
                                        reviews.value?.get(currentIndex)?.id?.let {
                                            viewModel.deleteReview(
                                                id,
                                                it
                                            )
                                        }
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        tint = if (currentIndex == 0) colorResource(id = R.color.hint_text) else colorResource(
                                            id = R.color.white
                                        )
                                    )
                                }
                            }


                            Spacer(modifier = Modifier.width(24.dp))

                            Row {
                                IconButton(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = if (currentIndex == 0) colorResource(id = R.color.dark_input) else colorResource(
                                                id = R.color.background
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 24.dp,
                                                bottomEnd = 24.dp
                                            )
                                        ),
                                    enabled = currentIndex != 0,
                                    onClick = {
                                        currentIndex--
                                        currentMode =
                                            if (reviews.value?.get(currentIndex)?.author?.userId != profileState.value.value) ReviewMode.ADD
                                            else ReviewMode.EDIT
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(8.dp)
                                            .height(16.dp),
                                        tint = if (currentIndex == 0) colorResource(id = R.color.hint_text) else colorResource(
                                            id = R.color.white
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = if (currentIndex == (reviews.value?.size?.minus(
                                                    1
                                                ) ?: 0)
                                            ) colorResource(id = R.color.dark_input) else colorResource(
                                                id = R.color.background
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clip(
                                            RoundedCornerShape(
                                                bottomStart = 24.dp,
                                                bottomEnd = 24.dp
                                            )
                                        ),
                                    enabled = currentIndex != (reviews.value?.size?.minus(
                                        1
                                    ) ?: 0),
                                    onClick = {
                                        currentIndex++
                                        currentMode =
                                            if (reviews.value?.get(currentIndex)?.author?.userId.toString() != profileState.value.value.toString()) ReviewMode.ADD
                                            else ReviewMode.EDIT
                                    }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_forward),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(8.dp)
                                            .height(16.dp),
                                        tint = if (currentIndex == (reviews.value?.size?.minus(
                                                1
                                            ) ?: 0)
                                        ) colorResource(id = R.color.hint_text) else colorResource(
                                            id = R.color.white
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    val obsDelete = viewModel.deletedReviewState.observeAsState()

    if (obsDelete.value?.isSuccess == true) {
        viewModel.emitNothingDeleted()
        if (currentIndex > 0) {
            currentIndex--
        }
        viewModel.updateReviewsOnly()
    }

    val obsAdd = viewModel.addedReviewState.observeAsState()

    if (obsAdd.value?.isSuccess == true) {
        viewModel.emitNothingAdded()
        viewModel.updateReviewsOnly()
    }

    val obsEdit = viewModel.editedReviewState.observeAsState()

    if (obsEdit.value?.isSuccess == true) {
        viewModel.emitNothingEdited()
        viewModel.updateReviewsOnly()
    }


    if (isDialogVisible) {
        if (currentMode == ReviewMode.ADD) {
            CustomDialog(
                onDismissRequest = {
                    isDialogVisible = false
                },
                mode = currentMode,
                onAccept = fun(
                    isAnonymous: Boolean,
                    rating: Int,
                    reviewText: String
                ) {
                    viewModel.addReview(id, isAnonymous, rating, reviewText)
                    isDialogVisible = false
                }
            )
        } else {
            CustomDialog(
                onDismissRequest = {
                    isDialogVisible = false
                },
                mode = currentMode,
                onAccept = fun(isAnonymous: Boolean, rating: Int, reviewText: String) {
                    reviews.value?.get(currentIndex)?.id.let {
                        if (it != null) {
                            viewModel.editReview(
                                id, it, isAnonymous, rating, reviewText
                            )
                        }
                    }
                    isDialogVisible = false
                },
                ratingValue = reviews.value?.get(currentIndex)?.rating ?: 5,
                reviewText = reviews.value?.get(currentIndex)?.reviewText ?: "",
                isAnonymous = reviews.value?.get(currentIndex)?.isAnonymous ?: false
            )
        }
    }
}