package com.example.testxml.domain.repository

import com.example.testxml.data.room.entities.Friend

interface FriendsRepository {
    suspend fun addFriend(userId:String, friendId:String, avatarLink:String?, name:String)

    suspend fun getFriendsByUser(userId: String): List<Friend>

    suspend fun deleteFriend(userId:String, friendId:String, avatarLink:String?, name:String)
}