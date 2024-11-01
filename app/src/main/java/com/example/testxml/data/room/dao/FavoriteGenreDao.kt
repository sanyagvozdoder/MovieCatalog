package com.example.testxml.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testxml.data.room.entities.FavoriteGenre

@Dao
interface FavoriteGenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteGenre(genre: FavoriteGenre)

    @Query("SELECT * FROM FavoriteGenre WHERE userId = :userId")
    suspend fun getFavoriteGenresByUser(userId: String): List<FavoriteGenre>

    @Delete
    suspend fun deleteFavoriteGenre(genre: FavoriteGenre)
}