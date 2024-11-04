package com.example.testxml.presentation.activities.movies_screen.components

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.domain.models.MovieGridCarousel
import com.squareup.picasso.Picasso

class GridCarouselAdapter : RecyclerView.Adapter<GridCarouselAdapter.PosterViewHolder>() {
    private val moviesList = mutableListOf<MovieGridCarousel>()
    private lateinit var onItemClickListener: (String)->Unit
    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image)
        val ratingTextView: TextView = itemView.findViewById(R.id.rating)
        val likedIcon:ImageView = itemView.findViewById(R.id.liked_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_carousel_content, parent, false)
        return PosterViewHolder(view)
    }

    fun setOnItemClickListener(onItemClickListener: (String)->Unit) {
        this.onItemClickListener = onItemClickListener
    }


    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        Picasso.get().load(moviesList[position].poster).into(holder.posterImageView)
        holder.posterImageView.setOnClickListener(View.OnClickListener {
            onItemClickListener(moviesList[position].id)
        })

        holder.ratingTextView.text = moviesList[position].rating.toString()
        val red = 255 -  (moviesList[position].rating * 25.5)
        val green = (moviesList[position].rating * 25.5)
        holder.ratingTextView.backgroundTintList = ColorStateList.valueOf(Color.rgb(red.toInt(),green.toInt(),0))

        if(moviesList[position].isFavorite){
           holder.likedIcon.visibility = VISIBLE
        }
    }

    fun addMovies(movies: List<MovieGridCarousel>) {
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }
}