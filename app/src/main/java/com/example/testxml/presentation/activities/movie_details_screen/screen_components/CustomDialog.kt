package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.testxml.R
import com.example.testxml.common.font.Manrope
import com.smarttoolfactory.slider.ColorfulIconSlider
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderColors
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    onDismissRequest:()->Unit
){
    var rating by remember {
        mutableStateOf<Float>(5f)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        ContentBlock(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.background),
                    shape = RoundedCornerShape(32.dp)
                )
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.55f)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Добавить отзыв",
                    color = colorResource(id = R.color.white),
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Добавить отзыв",
                    color = colorResource(id = R.color.desc),
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = rating.toInt().toString(),
                    fontFamily = Manrope,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.dark_input),
                            shape = CircleShape
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )


                ColorfulIconSlider(
                    value = rating,
                    onValueChange = {it->
                        rating = it
                    },
                    valueRange = 0f..10f,
                    trackHeight = 16.dp,
                    steps = 11,
                    colors = MaterialSliderDefaults.materialColors(
                        activeTrackColor = SliderBrushColor(brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.red),
                                colorResource(id = R.color.orange)
                            )
                        )),
                        inactiveTrackColor = SliderBrushColor(colorResource(id = R.color.dark_input)),
                        activeTickColor = SliderBrushColor(colorResource(id = R.color.dark_input)),
                        inactiveTickColor = SliderBrushColor(brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.red),
                                colorResource(id = R.color.orange)
                            )
                        ))
                    )
                ){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(14.dp)
                            .height(44.dp)
                            .background(colorResource(id = R.color.background))
                    ) {
                        Box(modifier = Modifier
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colorResource(id = R.color.red),
                                        colorResource(id = R.color.orange)
                                    )
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                        )
                    }
                }
            }
        }
    }
}

