package com.example.testxml.data.repository

import android.util.Log
import com.example.testxml.data.room.MoviesCatalogDatabase
import com.example.testxml.data.room.dao.FavoriteGenreDao
import com.example.testxml.data.room.entities.FavoriteGenre
import com.example.testxml.domain.repository.FavoriteGenreRepository

class FavoriteGenreRepositoryImpl constructor(
    private val db: MoviesCatalogDatabase = MoviesCatalogDatabase.getDatabase(),
    private val genreDao: FavoriteGenreDao = db.favoriteGenreDao()
) :FavoriteGenreRepository {
    override suspend fun addFavoriteGenre(userId: String, genreName: String) {
        genreDao.addFavoriteGenre(FavoriteGenre(userId,genreName))
    }

    override suspend fun getFavoriteGenresByUser(userId: String): List<FavoriteGenre> {
        return genreDao.getFavoriteGenresByUser(userId)
    }

    override suspend fun deleteFavoriteGenre(userId: String, genreName: String) {
        genreDao.deleteFavoriteGenre(FavoriteGenre(userId, genreName))
    }
}