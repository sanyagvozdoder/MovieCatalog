package com.example.testxml.domain.repository

import com.example.testxml.data.room.entities.FavoriteGenre

interface FavoriteGenreRepository {
    suspend fun addFavoriteGenre(userId: String, genreName: String)
    suspend fun getFavoriteGenresByUser(userId: String): List<FavoriteGenre>

    suspend fun deleteFavoriteGenre(userId: String, genreName: String)
}