package com.example.testxml.presentation.activities.favorite_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.testxml.R
import com.example.testxml.common.font.Manrope
import com.example.testxml.domain.models.MovieGridCarousel

@Composable
fun MovieItem(
    movie:MovieGridCarousel,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .height(160.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = Color(
                    (255 - (movie.rating *20).toInt()),
                        (movie.rating *20).toInt(),
                    0
                ),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Text(
                text = movie.rating.toString(),
                fontFamily = Manrope,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.white),
                fontSize = 12.sp
            )
        }
    }
}