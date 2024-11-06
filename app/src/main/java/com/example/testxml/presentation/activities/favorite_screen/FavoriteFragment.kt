package com.example.testxml.presentation.activities.favorite_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.testxml.R
import com.example.testxml.presentation.activities.favorite_screen.components.FavoritesScreen
import com.example.testxml.presentation.activities.main_activity.MainViewModel
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity

class FavoriteFragment : Fragment(R.layout.favorites_fragment) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.favorites_fragment, container, false)

        navController = findNavController()

        mainViewModel.loginData.observe(viewLifecycleOwner) { userLogin ->
            userLogin?.let {
                rootView.findViewById<ComposeView>(R.id.compose_view).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                    )
                    setContent {
                        FavoritesScreen(
                            userLogin,
                            placeholderOnClick = {
                                navController.navigate(R.id.nav_feed)
                            },
                            movieDetailsOnClick = fun(id:String){
                                startActivity(
                                    Intent(requireContext(), MovieDetailsActivity::class.java)
                                        .putExtra(MovieDetailsActivity.key, id)
                                        .putExtra(MovieDetailsActivity.login, userLogin))
                            }
                        )
                    }
                }
            }
        }

        return rootView
    }
}
