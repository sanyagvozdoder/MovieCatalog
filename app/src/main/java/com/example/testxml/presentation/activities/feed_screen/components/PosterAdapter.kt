package com.example.testxml.presentation.activities.feed_screen.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.squareup.picasso.Picasso

class PosterAdapter : RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {
    private val moviesList = mutableListOf<String>()
    private lateinit var onItemClickListener: (Int)->Unit

    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image)
    }

    fun setOnItemClickListener(onItemClickListener: (Int)->Unit) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_content, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val posterUrl = moviesList[position]
        Picasso.get().load(posterUrl).into(holder.posterImageView)
        holder.posterImageView.setOnClickListener (View.OnClickListener{
            onItemClickListener(position)
        })
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setMovies(movies: List<String>) {
        moviesList.clear()
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun addMovie(moviePoster: String) {
        moviesList.add(moviePoster)
        notifyItemInserted(moviesList.size - 1)
    }
}



