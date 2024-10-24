package com.example.testxml.presentation.activities.movies_screen

import android.R.attr.width
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.databinding.MoviesFragmentBinding
import com.example.testxml.presentation.activities.movies_screen.components.FavoriteCarouselAdapter


class MoviesFragment : Fragment(R.layout.movies_fragment) {
    private lateinit var binding: MoviesFragmentBinding
    val viewModel: MoviesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesFragmentBinding.bind(view)

        viewModel.getFavorites(requireContext())

        val carousel = binding.favoriteCarousel
        val manager = LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.HORIZONTAL
        manager.isSmoothScrollbarEnabled = true


        val customAdapter = FavoriteCarouselAdapter()

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(carousel)

        carousel.apply {
            layoutManager = manager
            adapter = customAdapter
        }

        carousel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val distanceFromStart = recyclerView.getChildAt(0).left

                val scaleFactorNext =
                    1.1f - (distanceFromStart / recyclerView.width.toFloat()) * 0.1f
                val scaleFactor = 1.1f - (scaleFactorNext - 1)

                for (i in 0 until recyclerView.childCount) {
                    if (i == 0) {
                        recyclerView.getChildAt(0).scaleX = scaleFactor
                        recyclerView.getChildAt(0).scaleY = scaleFactor
                    } else if (i == 1) {
                        recyclerView.getChildAt(1).scaleX = scaleFactorNext
                        recyclerView.getChildAt(1).scaleY = scaleFactorNext
                    } else {
                        recyclerView.getChildAt(i).scaleX = 1.0f
                        recyclerView.getChildAt(i).scaleY = 1.0f
                    }
                }
            }
        })

        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            if (state.isSuccess && state.movies != null) {
                customAdapter.setMovies(state.movies.map { it.poster })
            }
        }

        val favoritesText = binding.favoritesText
        val paint = favoritesText.paint
            val width = paint.measureText(favoritesText.text.toString())
            val shader = LinearGradient(
                0f, 0f, width.toFloat(), 0f,
                intArrayOf(Color.parseColor("#FFDF2800"), Color.parseColor("#FFFF6633")),
                null,
                Shader.TileMode.CLAMP
            )
            favoritesText.paint.shader = shader
    }
}