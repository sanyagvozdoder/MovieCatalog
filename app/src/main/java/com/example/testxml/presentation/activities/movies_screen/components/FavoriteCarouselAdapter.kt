package com.example.testxml.presentation.activities.movies_screen.components

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.squareup.picasso.Picasso

class FavoriteCarouselAdapter : RecyclerView.Adapter<FavoriteCarouselAdapter.PosterViewHolder>() {
    private val moviesList = mutableListOf<String>()
    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_content_image, parent, false)
        return PosterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val posterUrl = moviesList[position]
        Picasso.get().load(posterUrl).into(holder.posterImageView)
    }

    fun setMovies(movies: List<String>) {
        moviesList.clear()
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }
}