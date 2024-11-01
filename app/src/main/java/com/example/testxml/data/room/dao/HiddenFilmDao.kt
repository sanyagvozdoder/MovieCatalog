package com.example.testxml.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testxml.data.room.entities.HiddenFilm

@Dao
interface HiddenFilmDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addHiddenFilm(hiddenFilm: HiddenFilm)

    @Query("SELECT * FROM HiddenFilm WHERE userId = :userId")
    suspend fun getHiddenFilmsByUser(userId: String): List<HiddenFilm>
}