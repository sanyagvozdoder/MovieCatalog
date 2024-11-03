package com.example.testxml.domain.models

data class FriendModel(
    val userId: String,
    val friendId: String,
    val avatarLink: String?,
    val name: String
)