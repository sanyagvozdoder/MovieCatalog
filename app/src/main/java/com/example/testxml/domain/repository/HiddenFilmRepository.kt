package com.example.testxml.domain.repository

import com.example.testxml.data.room.entities.HiddenFilm

interface HiddenFilmRepository {
    suspend fun addHiddenFilm(userId:String, movieId:String)

    suspend fun getHiddenFilmsByUser(userId: String): List<HiddenFilm>
}