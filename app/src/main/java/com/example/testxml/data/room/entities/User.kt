package com.example.testxml.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class User(
    @PrimaryKey val userId: String,

    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val friends: List<Friend> = listOf(),

    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val favoriteGenres: List<FavoriteGenre> = listOf(),

    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val hiddenFilms: List<HiddenFilm> = listOf()
)
