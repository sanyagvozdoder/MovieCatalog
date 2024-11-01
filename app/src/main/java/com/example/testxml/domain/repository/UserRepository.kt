package com.example.testxml.domain.repository

import com.example.testxml.data.room.entities.User

interface UserRepository {
    suspend fun addUser(userId:String)
}