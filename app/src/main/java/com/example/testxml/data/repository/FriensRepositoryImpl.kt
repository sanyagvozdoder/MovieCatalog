package com.example.testxml.data.repository

import com.example.testxml.data.room.MoviesCatalogDatabase
import com.example.testxml.data.room.dao.FriendDao
import com.example.testxml.data.room.dao.HiddenFilmDao
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.repository.FriendsRepository

class FriendsRepositoryImpl constructor(
    private val db: MoviesCatalogDatabase = MoviesCatalogDatabase.getDatabase(),
    private val friendDao: FriendDao = db.friendDao()
) :FriendsRepository{
    override suspend fun addFriend(
        userId: String,
        friendId: String,
        avatarLink: String?,
        name: String
    ) {
        friendDao.addFriend(Friend(userId, friendId, avatarLink, name))
    }

    override suspend fun getFriendsByUser(userId: String): List<Friend> {
        return friendDao.getFriendsByUser(userId)
    }

    override suspend fun deleteFriend(
        userId: String,
        friendId: String,
        avatarLink: String?,
        name: String
    ) {
        friendDao.deleteFriend(Friend(userId, friendId, avatarLink, name))
    }

}