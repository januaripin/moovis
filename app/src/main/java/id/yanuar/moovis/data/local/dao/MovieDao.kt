package id.yanuar.moovis.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import id.yanuar.moovis.data.local.entity.Movie

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Dao
abstract class MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie")
    abstract fun findAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE nowPlaying = 1")
    abstract fun findNowPlayingMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE popular = 1")
    abstract fun findPopularMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE topRated = 1")
    abstract fun findTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE upcoming = 1")
    abstract fun findUpcomingMovies(): LiveData<List<Movie>>

}