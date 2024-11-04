package com.example.testxml.presentation.activities.movies_screen

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.example.testxml.R
import com.example.testxml.databinding.MoviesFragmentBinding
import com.example.testxml.presentation.activities.main_activity.MainViewModel
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.example.testxml.presentation.activities.movies_screen.components.FavoriteCarouselAdapter
import com.example.testxml.presentation.activities.movies_screen.components.GridCarouselAdapter
import com.example.testxml.presentation.activities.movies_screen.components.GridSpacingItemDecoration
import com.example.testxml.presentation.activities.movies_screen.components.TopCarouselAdapter



class MoviesFragment : Fragment(R.layout.movies_fragment) {
    private lateinit var binding: MoviesFragmentBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var userLogin:String
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.loginData.observe(viewLifecycleOwner) { value ->
            userLogin = value
            viewModel.getGenres(value)
        }

        binding = MoviesFragmentBinding.bind(view)

        val randomBtn = binding.randomButton

        viewModel.getFavorites()
        viewModel.getTopMovies(1)

        val carousel = binding.favoriteCarousel
        val manager = LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.HORIZONTAL
        manager.isSmoothScrollbarEnabled = true

        val customAdapter = FavoriteCarouselAdapter()

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


        val topCarousel = binding.topCarousel
        val topManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val topAdapter = TopCarouselAdapter(requireContext())

        topAdapter.setOnWatchClickListener {
            startActivity(
                Intent(requireContext(), MovieDetailsActivity::class.java)
                .putExtra(MovieDetailsActivity.key, it)
                .putExtra(MovieDetailsActivity.login, userLogin))
        }

        viewModel.stateGenre.observe(viewLifecycleOwner){
            if(it.isSuccess){
                it.value?.let { it1 -> topAdapter.setGenres(it1) }
            }
        }

        topCarousel.apply {
            layoutManager = topManager
            adapter = topAdapter
        }

        val progressList = listOf(
            binding.progress1,
            binding.progress2,
            binding.progress3,
            binding.progress4,
            binding.progress5
        )

        progressList.forEach {
            it.isIndeterminate = false
            it.progressDrawable = getDrawable(requireContext(), R.drawable.progress_bar_colors)
        }


        var currentProgressAnimator: ValueAnimator? = null

        fun animateProgressBar(progressBar: ProgressBar) {
            currentProgressAnimator = ValueAnimator.ofInt(0, 100).apply {
                duration = 5000L

                addUpdateListener { animation ->
                    progressBar.progress = animation.animatedValue as Int
                    if(progressBar.id == R.id.progress_5 && progressBar.progress == 100){
                        progressBar.progress = 0
                    }
                }

                start()
            }
        }

        fun completeProgressBar(progressBar: ProgressBar) {
            currentProgressAnimator?.cancel()
            progressBar.progress = 100
        }

        fun resetProgressBar(progressBar: ProgressBar){
            currentProgressAnimator?.cancel()
            progressBar.progress = 0
        }


        val handler = Handler(Looper.getMainLooper())
        var currentPosition = 0

        val runnable = object:Runnable {
            override fun run() {
                currentPosition += 1

                if (currentPosition == topAdapter.itemCount) {
                    currentPosition = 0
                    progressList.forEach {
                        it.progress = 0
                    }
                }

                topCarousel.smoothScrollToPosition(currentPosition)
                animateProgressBar(progressList[currentPosition])

                handler.postDelayed(this, 5000)
            }
        }


        fun startCarouselAnimation(delay:Long) {
            completeProgressBar(progressList[currentPosition])
            animateProgressBar(progressList[currentPosition])
            handler.postDelayed(runnable, delay)
        }

        startCarouselAnimation(5000)

        topCarousel.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val screenWidth = topCarousel.width
                val clickX = event.x

                if (clickX > screenWidth / 2) {
                    completeProgressBar(progressList[currentPosition])
                    currentPosition += 1

                    if (currentPosition == topAdapter.itemCount) {
                        currentPosition = 0
                        progressList.forEach {
                            it.progress = 0
                        }
                    }
                } else {
                    resetProgressBar(progressList[currentPosition])
                    currentPosition -= 1
                    if(currentPosition == -1){
                        currentPosition = topAdapter.itemCount - 1
                        progressList.forEach {
                            it.progress = 100
                        }
                    }
                    progressList[currentPosition].progress = 0
                }

                topCarousel.smoothScrollToPosition(currentPosition)

                handler.removeCallbacks(runnable)
                handler.postDelayed({ startCarouselAnimation(5000) }, 0)
            }
            true
        }


        val gridCarousel = binding.allCarousel
        gridCarousel.addItemDecoration(GridSpacingItemDecoration(3,8))

        val gridManager = object:GridLayoutManager(requireContext(),3){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        val gridAdapter = GridCarouselAdapter()

        gridAdapter.setOnItemClickListener {
            startActivity(
                Intent(requireContext(), MovieDetailsActivity::class.java)
                    .putExtra(MovieDetailsActivity.key,it)
                    .putExtra(MovieDetailsActivity.login, userLogin))
        }

        gridCarousel.apply {
            layoutManager = gridManager
            adapter = gridAdapter
        }

        var currentPage = 3

        val scrollView = binding.scrollView
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v, _, scrollY, _, _ ->
            if(scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                if (currentPage != 6) {
                    viewModel.getMovies(currentPage++)
                }
            }
        })

        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            if (state.isSuccess && state.movies != null) {
                customAdapter.setMovies(state.movies.map { it.poster })
                viewModel.getMovies(2)
            }
        }

        viewModel.topState.observe(viewLifecycleOwner){state->
            if (state.isSuccess && state.movies != null){
                topAdapter.setMovies(state.movies)
            }
        }

        viewModel.gridState.observe(viewLifecycleOwner){state->
            if (state.isSuccess && state.movies != null) {
                gridAdapter.addMovies(state.movies)
                viewModel.getRandomMovie()
            }
        }

        viewModel.randomState.observe(viewLifecycleOwner){state->
            if(state.isSuccess){
                randomBtn.setOnClickListener{
                    startActivity(
                        Intent(requireContext(), MovieDetailsActivity::class.java)
                            .putExtra(MovieDetailsActivity.key,state.value?.id)
                            .putExtra(MovieDetailsActivity.login, userLogin))
                    viewModel.getRandomMovie()
                }
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

        val allText = binding.allFilmsText
        val paintAll = allText.paint
            val widthAll = paintAll.measureText(favoritesText.text.toString())
            val shaderAll = LinearGradient(
                0f, 0f, widthAll.toFloat(), 0f,
                intArrayOf(Color.parseColor("#FFDF2800"), Color.parseColor("#FFFF6633")),
                null,
                Shader.TileMode.CLAMP
            )
        allText.paint.shader = shaderAll
    }
}