package com.example.testxml.presentation.activities.feed_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.databinding.FeedFragmentBinding
import com.example.testxml.presentation.activities.feed_screen.components.PosterAdapter
import com.example.testxml.presentation.activities.main_activity.MainViewModel
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting

class FeedFragment: Fragment(R.layout.feed_fragment) {
    private lateinit var binding: FeedFragmentBinding
    private var currentIndex = 0
    private lateinit var userLogin:String
    val viewModel:FeedViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.loginData.observe(viewLifecycleOwner) { value ->
            userLogin = value
            viewModel.getHiddenFilms(value)
        }

        binding = FeedFragmentBinding.bind(view)

        val cardStackView = binding.poster

        var stateForObserve = 0

        val title = binding.title
        val info = binding.yearcountry
        val genre1 = binding.genre1
        val genre2 = binding.genre2
        val genre3 = binding.genre3
        val placeholder = binding.posterPlaceholder

        val genresIsFavorite = mutableListOf<Boolean>(
            false,false,false
        )

        genre1.setOnClickListener {
            if(genre1.text != ""){
                if(genresIsFavorite[0]){
                    viewModel.deleteGenre(userLogin, genre1.text.toString())
                    genre1.background = resources.getDrawable(R.drawable.edit_shape)
                }
                else{
                    viewModel.addGenre(userLogin, genre1.text.toString())
                    genre1.background = resources.getDrawable(R.drawable.orange_gradient)
                }
                genresIsFavorite[0] = !genresIsFavorite[0]
            }
        }
        genre2.setOnClickListener {
            if(genre2.text != ""){
                if(genresIsFavorite[1]){
                    viewModel.deleteGenre(userLogin, genre2.text.toString())
                    genre2.background = resources.getDrawable(R.drawable.edit_shape)
                }
                else{
                    viewModel.addGenre(userLogin, genre2.text.toString())
                    genre2.background = resources.getDrawable(R.drawable.orange_gradient)
                }
                genresIsFavorite[1] = !genresIsFavorite[1]
            }
        }
        genre3.setOnClickListener {
            if(genre3.text != ""){
                if(genresIsFavorite[2]){
                    viewModel.deleteGenre(userLogin, genre3.text.toString())
                    genre3.background = resources.getDrawable(R.drawable.edit_shape)
                }
                else{
                    viewModel.addGenre(userLogin, genre3.text.toString())
                    genre3.background = resources.getDrawable(R.drawable.orange_gradient)
                }
                genresIsFavorite[2] = !genresIsFavorite[2]
            }
        }


        fun isFavoriteGenre(text:String, index:Int): Drawable {
            return if(viewModel.stateGenre.value?.value?.any { it == text } == true){
                genresIsFavorite[index] = true
                resources.getDrawable(R.drawable.orange_gradient)
            } else{
                genresIsFavorite[index] = false
                resources.getDrawable(R.drawable.edit_shape)
            }
        }

        fun updateCurrentMovie(movie: Movie?){
            if (movie != null) {
                title.text = movie.name
                info.text = movie.country + " • " + movie.year
                genre1.visibility = VISIBLE
                genre2.visibility = GONE
                genre3.visibility = GONE
                genre1.text = movie.genres[0].name

                genre1.background = isFavoriteGenre(genre1.text.toString(), 0)

                if(movie.genres.size >= 2){
                    genre2.visibility = VISIBLE
                    genre2.text = movie.genres[1].name
                    genre2.background = isFavoriteGenre(genre2.text.toString(), 1)
                }
                if(movie.genres.size >= 3){
                    genre3.visibility = VISIBLE
                    genre3.text = movie.genres[2].name
                    genre3.background = isFavoriteGenre(genre3.text.toString(), 2)
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
                if(direction == Direction.Left){
                    viewModel.hideFilm(userLogin, viewModel._movies[currentIndex-1].id)
                }
                if (customAdapter.itemCount - currentIndex <= 2
                    &&
                    viewModel.state.value?.movies?.pageInfo?.currentPage?.plus(1)!! <= viewModel.state.value?.movies?.pageInfo?.pageCount!!
                ){
                    viewModel.getMovies(viewModel.state.value?.movies?.pageInfo?.currentPage?.plus(1)!!)
                }
                if(customAdapter.itemCount - currentIndex == 0
                    &&
                    viewModel.state.value?.movies?.pageInfo?.currentPage?.plus(1)!! > viewModel.state.value?.movies?.pageInfo?.pageCount!!
                    ){
                    genre2.visibility = GONE
                    genre3.visibility = GONE
                    genre1.visibility = GONE
                    title.text = "Похоже фильмы закончились"
                    info.text = "приходите позже :)"
                    placeholder.visibility = VISIBLE
                }
                viewModel.getGenres(userLogin)
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
                .putExtra(MovieDetailsActivity.key, viewModel._movies[currentIndex-1].id)
                .putExtra(MovieDetailsActivity.login, userLogin))
        }

        cardStackView.apply {
            this.layoutManager = cardManager
            this.adapter = customAdapter
        }


        viewModel.state.observe(viewLifecycleOwner){state->
            if (state.isSuccess && state.movies != null && stateForObserve == 0 ){
                if(viewModel._movies.size != 0){
                    customAdapter.setMovies(viewModel._movies.map { it.poster })
                    stateForObserve = 1
                    viewModel.getGenres(userLogin)
                }
                else{
                    genre2.visibility = GONE
                    genre3.visibility = GONE
                    genre1.visibility = GONE
                    title.text = "Похоже фильмы закончились"
                    info.text = "приходите позже :)"
                    placeholder.visibility = VISIBLE
                }

            }
            else if(state.isSuccess){
                if(viewModel._movies.size != 0){
                    viewModel._movies.takeLast(state.added)?.forEach { movie ->
                        customAdapter.addMovie(movie.poster)
                    }
                }
                else{
                    genre2.visibility = GONE
                    genre3.visibility = GONE
                    genre1.visibility = GONE
                    title.text = "Похоже фильмы закончились"
                    info.text = "приходите позже :)"
                    placeholder.visibility = VISIBLE
                }
            }
        }

        viewModel.hiddenState.observe(viewLifecycleOwner){
            if(it.isSuccess){
                viewModel.getMovies(1)
            }
        }

        viewModel.stateGenre.observe(viewLifecycleOwner){
            if(it.isSuccess){
                updateCurrentMovie(viewModel._movies[currentIndex++])
            }
        }
    }
}