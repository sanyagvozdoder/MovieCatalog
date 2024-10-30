package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import android.support.v4.os.IResultReceiver2.Default
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testxml.R
import com.example.testxml.common.font.Manrope

@Composable
fun DefaultBlock(
    painter: Painter,
    text:String,
    content:@Composable()(ColumnScope.() -> Unit)
){
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
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = text,
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.desc),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}