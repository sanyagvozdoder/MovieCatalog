package com.example.testxml.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["userId", "friendId"],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Friend(
    val userId: String,
    val friendId: String,
    val avatarLink: String,
    val name: String
)
