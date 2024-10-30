package com.example.testxml.data.remote.dto

import com.example.testxml.domain.models.MovieDetail
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

data class MovieDetailDto(
    val ageLimit: Int,
    val budget: Int,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewDetail>,
    val tagline: String,
    val time: Int,
    val year: Int
)


fun MovieDetailDto.toMovieDetail():MovieDetail{
    val hours = this.time/60
    val mins = this.time - (this.time/60) * 60
    var formatTime = ""

    if(hours > 0){
        formatTime = hours.toString() +"ч " + mins.toString() + "мин"
    }
    else{
        formatTime = mins.toString() + "мин"
    }

    val format = DecimalFormat("#,##0.0", DecimalFormatSymbols(Locale.FRANCE))
    val averageRating = this.reviews.sumOf { it.rating }.toFloat() / this.reviews.size

    return MovieDetail(
        ageLimit = this.ageLimit.toString() + "+",
        budget= "$ " + formatMoney(this.budget),
        country= this.country,
        description= this.description,
        director= this.director,
        fees= "$ " + formatMoney(this.fees),
        genres= this.genres,
        id= this.id,
        name= this.name,
        poster= this.poster,
        reviews= this.reviews,
        tagline= this.tagline,
        time= formatTime,
        year= this.year,
        appRating = format.format(averageRating).replace(",", ".").toFloat()
    )
}

fun formatMoney(value: Int): String {
    if(value.toString().length > 9){
        val rounded = value / 1_000_000_000
        val mils = (value - rounded * 1_000_000_000) / 1_000_000
        return String.format(
            "%01d %03d %03d %03d",
            rounded,
            mils,0,0
        )
    }
    else if(value.toString().length > 6){
        val rounded = value / 1_000_000
        return String.format(
            "%01d %03d %03d",
            rounded,
            0,0
        )
    }
    else if(value.toString().length > 3){
        val rounded = value / 1_000
        return String.format(
            "%01d %03d",
            rounded,
            0
        )
    }
    else{
        return value.toString()
    }
}