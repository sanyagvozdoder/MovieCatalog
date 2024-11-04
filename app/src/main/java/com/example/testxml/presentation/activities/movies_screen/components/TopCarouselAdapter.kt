package com.example.testxml.presentation.activities.movies_screen.components

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.testxml.R
import com.example.testxml.domain.models.MovieTopCarousel
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class TopCarouselAdapter(private val context: Context) : RecyclerView.Adapter<TopCarouselAdapter.PosterViewHolder>() {
    private val moviesList = mutableListOf<MovieTopCarousel>()
    private val genreList = mutableListOf<String>()
    private lateinit var onWatchClickListener: (String)->Unit
    class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.poster_image)
        val titleView: TextView = itemView.findViewById(R.id.title)
        val genre1View:TextView = itemView.findViewById(R.id.genre1)
        val genre2View:TextView = itemView.findViewById(R.id.genre2)
        val genre3View:TextView = itemView.findViewById(R.id.genre3)
        val buttonWatch:Button = itemView.findViewById(R.id.button_watch)
    }

    fun setOnWatchClickListener(onWatchClickListener: (String)->Unit) {
        this.onWatchClickListener = onWatchClickListener
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
        holder.titleView.text = moviesList[position].name

        var maxindex = 0

        val genresIndex = mutableListOf<Int>()

        for(i in 1..2){
            if(moviesList[position].genres[i].name.length >= moviesList[position].genres[maxindex].name.length ){
                genresIndex.add(maxindex)
                maxindex = i
            }
            else{
                genresIndex.add(i)
            }
        }

        holder.genre3View.text = moviesList[position].genres[maxindex].name
        holder.genre1View.background =
            if(genreList.any { it ==  holder.genre1View.text.toString() })
                getDrawable(context, R.drawable.orange_gradient)
            else
                getDrawable(context, R.drawable.edit_shape)

        holder.genre2View.text = moviesList[position].genres[genresIndex[0]].name
        holder.genre2View.background =
            if(genreList.any { it ==  holder.genre2View.text.toString() })
                getDrawable(context, R.drawable.orange_gradient)
            else
                getDrawable(context, R.drawable.edit_shape)

        holder.genre1View.text = moviesList[position].genres[genresIndex[1]].name
        holder.genre3View.background =
            if(genreList.any { it ==  holder.genre3View.text.toString() })
                getDrawable(context, R.drawable.orange_gradient)
            else
                getDrawable(context, R.drawable.edit_shape)

        Picasso.get().load(moviesList[position].poster).into(holder.posterImageView)

        holder.buttonWatch.setOnClickListener (View.OnClickListener{
            onWatchClickListener(moviesList[position].id)
        })
    }

    fun setMovies(movies: List<MovieTopCarousel>) {
        moviesList.clear()
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    fun setGenres(genres:List<String>){
        genreList.addAll(genres)
        notifyDataSetChanged()
    }
}