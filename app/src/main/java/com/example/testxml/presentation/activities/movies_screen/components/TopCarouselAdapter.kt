package com.example.testxml.presentation.activities.movies_screen.components

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.domain.models.MovieTopCarousel
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class TopCarouselAdapter : RecyclerView.Adapter<TopCarouselAdapter.PosterViewHolder>() {
    private val moviesList = mutableListOf<MovieTopCarousel>()
    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image)
        val titleView: TextView = itemView.findViewById(R.id.title)
        val genre1View:TextView = itemView.findViewById(R.id.genre1)
        val genre2View:TextView = itemView.findViewById(R.id.genre2)
        val genre3View:TextView = itemView.findViewById(R.id.genre3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_recycler_content, parent, false)
        return PosterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        Log.d("penisss", holder.titleView.visibility.toString())
        holder.titleView.text = moviesList[position].name
        holder.genre1View.text = moviesList[position].genres[0].name
        holder.genre2View.text = moviesList[position].genres[1].name
        holder.genre3View.text = moviesList[position].genres[2].name
        Picasso.get().load(moviesList[position].poster).into(holder.posterImageView)
    }

    fun setMovies(movies: List<MovieTopCarousel>) {
        moviesList.clear()
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }
}