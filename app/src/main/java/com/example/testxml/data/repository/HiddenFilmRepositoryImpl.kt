package com.example.testxml.data.repository

import com.example.testxml.data.room.MoviesCatalogDatabase
import com.example.testxml.data.room.dao.HiddenFilmDao
import com.example.testxml.data.room.entities.HiddenFilm
import com.example.testxml.domain.repository.HiddenFilmRepository

class HiddenFilmRepositoryImpl constructor(
    private val db: MoviesCatalogDatabase = MoviesCatalogDatabase.getDatabase(),
    private val hiddenFilmDao: HiddenFilmDao= db.hiddenFilmDao()
) : HiddenFilmRepository {
    override suspend fun addHiddenFilm(userId: String, movieId: String) {
        hiddenFilmDao.addHiddenFilm(HiddenFilm(userId,movieId))
    }

    override suspend fun getHiddenFilmsByUser(userId: String): List<HiddenFilm> {
        return hiddenFilmDao.getHiddenFilmsByUser(userId)
    }

}