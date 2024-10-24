package com.example.testxml.presentation.activities.movies_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.databinding.MoviesFragmentBinding
import com.example.testxml.presentation.activities.movies_screen.components.FavoriteCarouselAdapter
import kotlin.math.abs

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

        carousel.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()

                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)

                    if (recyclerView.getChildAdapterPosition(child) == firstVisiblePosition) {
                        child.animate()
                            .scaleX(1.2f)
                            .scaleY(1.1f)
                            .start()
                    } else {
                        child.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .start()
                    }
                }
            }
        })

        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            if (state.isSuccess && state.movies != null) {
                customAdapter.setMovies(state.movies.map { it.poster })
            }
        }
    }
}