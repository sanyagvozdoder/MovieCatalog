package com.example.testxml.presentation.activities.movies_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testxml.R
import com.example.testxml.databinding.MoviesFragmentBinding
import com.example.testxml.presentation.activities.movies_screen.components.FavoriteCarouselAdapter

class MoviesFragment: Fragment(R.layout.movies_fragment){
    private lateinit var binding: MoviesFragmentBinding
    val viewModel:MoviesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesFragmentBinding.bind(view)

        viewModel.getFavorites(requireContext())

        val carousel = binding.favoriteCarousel
        val manager = LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.HORIZONTAL

        val customAdapter = FavoriteCarouselAdapter()

        carousel.apply {
            layoutManager = manager
            adapter = customAdapter
        }

        viewModel.favoriteState.observe(viewLifecycleOwner){state->
            Log.d("penis", state.toString())
            if(state.isSuccess && state.movies != null){
                customAdapter.setMovies(state.movies.map { it.poster })
            }
        }
    }
}