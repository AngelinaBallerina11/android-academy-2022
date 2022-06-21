package com.strv.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.strv.movies.data.database.data.dao.MoviesDao
import com.strv.movies.data.database.data.entity.GenreEntity
import com.strv.movies.data.database.data.entity.MovieDetailEntity
import com.strv.movies.data.database.data.entity.MovieGenreEntity
import com.strv.movies.data.database.MoviesDatabase.Companion.DATABASE_VERSION

@Database(
    entities = [
        MovieDetailEntity::class, GenreEntity::class, MovieGenreEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movies_database"
        const val DATABASE_VERSION = 1
    }

    abstract fun getMoviesDao(): MoviesDao
}
