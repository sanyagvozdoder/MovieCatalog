package com.example.testxml.presentation.activities.feed_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.databinding.FeedFragmentBinding
import com.example.testxml.presentation.activities.feed_screen.components.PosterAdapter
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting

class FeedFragment: Fragment(R.layout.feed_fragment) {
    private lateinit var binding: FeedFragmentBinding
    private var currentIndex = 0
    val viewModel:FeedViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FeedFragmentBinding.bind(view)

        val cardStackView = binding.poster

        if(savedInstanceState == null){
            viewModel.getMovies(1)
        }

        var stateForObserve = 0

        val title = binding.title
        val info = binding.yearcountry
        val genre1 = binding.genre1
        val genre2 = binding.genre2
        val genre3 = binding.genre3

        fun updateCurrentMovie(movie: Movie?){
            if (movie != null) {
                title.text = movie.name
                info.text = movie.country + " • " + movie.year
                genre2.visibility = GONE
                genre3.visibility = GONE
                genre1.text = movie.genres[0].name
                if(movie.genres.size >= 2){
                    genre2.visibility = VISIBLE
                    genre2.text = movie.genres[1].name
                }
                if(movie.genres.size >= 3){
                    genre3.visibility = VISIBLE
                    genre3.text = movie.genres[2].name
                }
            }
        }

        val customAdapter = PosterAdapter()

        val cardManager = CardStackLayoutManager(requireContext(), object:CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right){
                    viewModel.addFavorites(viewModel._movies[currentIndex-1].id)
                }
                if (customAdapter.itemCount - currentIndex == 2){
                    viewModel.getMovies(viewModel.state.value?.movies?.pageInfo?.currentPage?.plus(1) ?: 1)
                }
                updateCurrentMovie(viewModel._movies[currentIndex++])
            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }
        })

        cardManager.setMaxDegree(25f)
        cardManager.setCanScrollVertical(false)
        cardManager.setDirections(listOf(Direction.Right,Direction.Left))
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

        cardManager.setSwipeAnimationSetting(setting)

        customAdapter.setOnItemClickListener {
            startActivity(Intent(requireContext(),MovieDetailsActivity::class.java)
                .putExtra(MovieDetailsActivity.key, viewModel._movies[currentIndex-1].id))
        }

        cardStackView.apply {
            this.layoutManager = cardManager
            this.adapter = customAdapter
        }


        viewModel.state.observe(viewLifecycleOwner){state->
            if (state.isSuccess && state.movies != null && stateForObserve == 0 ){
                updateCurrentMovie(viewModel.state.value?.movies?.movies?.get(currentIndex++))
                customAdapter.setMovies(state.movies.movies.map { it.poster })
                stateForObserve = 1
                viewModel.addFavorites(viewModel._movies[0].genres[0].id)
            }
            else if(state.isSuccess){
                state.movies?.movies?.forEach{customAdapter.addMovie(it.poster)}
            }
        }
    }
}