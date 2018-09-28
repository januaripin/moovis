package id.yanuar.moovis.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.dao.MovieDao
import id.yanuar.moovis.data.local.dao.MovieDetailsDao
import id.yanuar.moovis.data.local.entity.Genre
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.local.entity.MovieDetails

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Database(entities = [Genre::class, Movie::class, MovieDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao

    abstract fun movieDetailsDao(): MovieDetailsDao
}