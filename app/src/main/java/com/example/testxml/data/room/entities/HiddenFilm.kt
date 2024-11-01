package com.example.testxml.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    primaryKeys = ["userId", "movieId"],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HiddenFilm(
    val userId: String,
    val movieId: String
)