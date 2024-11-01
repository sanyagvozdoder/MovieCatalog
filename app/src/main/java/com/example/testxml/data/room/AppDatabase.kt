package com.example.testxml.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testxml.core.MyApplication
import com.example.testxml.data.room.dao.FavoriteGenreDao
import com.example.testxml.data.room.dao.FriendDao
import com.example.testxml.data.room.dao.HiddenFilmDao
import com.example.testxml.data.room.dao.UserDao
import com.example.testxml.data.room.entities.FavoriteGenre
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.data.room.entities.HiddenFilm
import com.example.testxml.data.room.entities.User

@Database(
    entities = [User::class, Friend::class, FavoriteGenre::class, HiddenFilm::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesCatalogDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao
    abstract fun favoriteGenreDao(): FavoriteGenreDao
    abstract fun hiddenFilmDao(): HiddenFilmDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesCatalogDatabase? = null

        fun getDatabase(): MoviesCatalogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    MyApplication.instance,
                    MoviesCatalogDatabase::class.java,
                    "movies_catalog_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}