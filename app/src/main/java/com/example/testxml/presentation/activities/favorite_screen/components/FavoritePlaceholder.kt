package com.example.testxml.presentation.activities.favorite_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testxml.R
import com.example.testxml.common.font.Manrope

@Composable
fun FavoritePlaceholder(
    onClick:()->Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(painter = painterResource(
                id = R.drawable.favorites_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Здесь пока пусто",
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    fontSize = 24.sp
                )
                Text(
                    text = "Добавьте любимые жанры и фильмы, чтобы вернуться к ним позже",
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                TextButton(
                    modifier = Modifier.background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.red),
                                colorResource(id = R.color.orange)
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = onClick
                ) {
                    Text(
                        text = "Найти фильм для себя",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp
                    )
                }
            }
        }

        Text(
            text = "Избранное",
            modifier = Modifier.padding(vertical=32.dp, horizontal = 24.dp),
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            fontSize = 24.sp
        )
    }
}