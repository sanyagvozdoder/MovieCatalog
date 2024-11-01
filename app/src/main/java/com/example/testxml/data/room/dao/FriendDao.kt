package com.example.testxml.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testxml.data.room.entities.Friend

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFriend(friend: Friend)

    @Query("SELECT * FROM Friend WHERE userId = :userId")
    suspend fun getFriendsByUser(userId: String): List<Friend>

    @Delete
    suspend fun deleteFriend(friend: Friend)
}