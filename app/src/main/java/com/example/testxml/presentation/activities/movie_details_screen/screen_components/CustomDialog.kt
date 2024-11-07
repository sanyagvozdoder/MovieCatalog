package com.example.testxml.presentation.activities.movie_details_screen.screen_components

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.testxml.R
import com.example.testxml.common.font.Manrope
import com.example.testxml.presentation.activities.movie_details_screen.util.ReviewMode
import com.smarttoolfactory.slider.ColorfulIconSlider
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderColors
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import kotlin.math.ceil
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    onDismissRequest:()->Unit,
    mode:ReviewMode,
    reviewText:String = "",
    ratingValue:Int = 5,
    isAnonymous:Boolean = false,
    onAccept:(Boolean,Int,String)->Unit
){
    var rating by remember {
        mutableStateOf<Int>(ratingValue)
    }

    var descValue by remember { mutableStateOf(reviewText) }

    var isSwitchChecked by remember { mutableStateOf(isAnonymous) }

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
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
            ) {
                Text(
                    text = if(mode == ReviewMode.ADD) "Добавить отзыв" else "Редактировать отзыв",
                    color = colorResource(id = R.color.white),
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Оценка",
                    color = colorResource(id = R.color.desc),
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                ) {
                    ColorfulIconSlider(
                        value = rating.toFloat(),
                        onValueChange = {it->
                            rating = it.toInt()
                        },
                        valueRange = 0f..10f,
                        trackHeight = 16.dp,
                        steps = 9,
                        colors = MaterialSliderDefaults.materialColors(
                            activeTrackColor = SliderBrushColor(brush = Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.red),
                                    colorResource(id = R.color.orange)
                                )
                            )),
                            inactiveTrackColor = SliderBrushColor(colorResource(id = R.color.dark_input)),
                            activeTickColor = SliderBrushColor(Color.Transparent),
                            inactiveTickColor = SliderBrushColor(Color.Transparent)
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .width(48.dp)
                                    .background(
                                        color = colorResource(id = R.color.dark_input),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = rating.toString(),
                                    fontFamily = Manrope,
                                    color = colorResource(id = R.color.white),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .width(19.dp)
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

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(top = 68.dp, start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 0..10) {
                            Box(
                                modifier = if (i < rating) {
                                    Modifier
                                        .size(4.dp)
                                        .background(
                                            color = colorResource(id = R.color.white),
                                            shape = CircleShape
                                        )
                                }
                                else if (i > rating){
                                    Modifier
                                        .size(4.dp)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    colorResource(id = R.color.red),
                                                    colorResource(id = R.color.orange)
                                                )
                                            ),
                                            shape = CircleShape
                                        )
                                }
                                else{
                                    Modifier
                                        .size(4.dp)
                                        .background(
                                            color = Color.Transparent,
                                            shape = CircleShape
                                        )
                                }
                            )
                        }
                    }
                }

                TextField(
                    value = descValue,
                    onValueChange = {
                        descValue = it
                    },
                    modifier = Modifier
                        .offset(y = (-40).dp)
                        .height(120.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            text = "Текст отзыва",
                            fontFamily = Manrope,
                            color = colorResource(id = R.color.hint_text),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp)
                    },
                    textStyle = TextStyle(
                        fontFamily = Manrope,
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.dark_input),
                        unfocusedContainerColor = colorResource(id = R.color.dark_input),
                        disabledContainerColor = colorResource(id = R.color.dark_input),
                        cursorColor = colorResource(id = R.color.white),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-32).dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Анонимный отзыв",
                        color = colorResource(id = R.color.desc),
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    GradientSwitch(
                        checked = isSwitchChecked,
                        onCheckedChange = {
                            isSwitchChecked = it
                        },
                        checkedTrackColor = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.red),
                                colorResource(id = R.color.orange)
                            )
                        ),
                        uncheckedTrackColor = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.dark_input),
                                colorResource(id = R.color.dark_input)
                            )
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().offset(y = (-8).dp).absolutePadding(bottom = 0.dp)
                ) {
                    TextButton(
                        onClick = {
                            onAccept(isSwitchChecked, rating, descValue)
                        },
                        modifier = Modifier
                            .background(brush = Brush.horizontalGradient(
                                colors = if(descValue!="") {
                                    listOf(
                                        colorResource(id = R.color.red),
                                        colorResource(id = R.color.orange)
                                    )
                                }
                                else{
                                    listOf(
                                        colorResource(id = R.color.dark_input),
                                        colorResource(id = R.color.dark_input)
                                    )
                                }
                            ),
                                shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        enabled = descValue!=""
                    ) {
                        Text(
                            text = "Отправить",
                            color = colorResource(id = R.color.white),
                            fontFamily = Manrope,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}

