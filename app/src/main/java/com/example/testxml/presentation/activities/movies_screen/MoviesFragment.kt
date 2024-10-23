package com.example.testxml.presentation.activities.movies_screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import com.example.testxml.R
import com.example.testxml.databinding.MoviesFragmentBinding

class MoviesFragment: Fragment(R.layout.movies_fragment){
    private lateinit var binding: MoviesFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesFragmentBinding.bind(view)

        val carousel = binding.carousel
        carousel.setAdapter(object : Carousel.Adapter{
            override fun count(): Int {
                TODO("Not yet implemented")
            }

            override fun populate(view: View?, index: Int) {
                TODO("Not yet implemented")
            }

            override fun onNewItem(index: Int) {
                TODO("Not yet implemented")
            }

        })
    }
}