package com.example.testxml.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class User(
    @PrimaryKey val userId: String
)
