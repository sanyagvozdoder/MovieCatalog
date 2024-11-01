package com.example.testxml.data.repository

import com.example.testxml.data.room.MoviesCatalogDatabase
import com.example.testxml.data.room.dao.UserDao
import com.example.testxml.data.room.entities.User
import com.example.testxml.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val db:MoviesCatalogDatabase = MoviesCatalogDatabase.getDatabase(),
    private val userDao: UserDao = db.userDao()
) : UserRepository {
    override suspend fun addUser(userId: String) {
        userDao.addUser(User(userId))
    }

}