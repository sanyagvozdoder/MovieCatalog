package com.example.testxml.presentation.activities.favorite_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.testxml.R

class FavoriteFragment: Fragment(R.layout.favorites_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container,false).apply{
            findViewById<ComposeView>(R.id.compose_view).apply{
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                )
                setContent {
                    Text(
                        text = "ПИДАРААААААААААСССС",
                        style = TextStyle(fontSize = 30.sp),
                        color = Color.White
                    )
                }
            }
        }
    }
}