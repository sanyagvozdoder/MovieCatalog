package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.testxml.R

@Composable
fun ContentBlock(
    modifier: Modifier = Modifier,
    content: @Composable()(BoxScope.()->Unit)
){
    Box(
        content = content,
        modifier = modifier
    )
}