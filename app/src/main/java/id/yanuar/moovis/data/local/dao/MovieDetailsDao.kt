package id.yanuar.moovis.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import id.yanuar.moovis.data.local.entity.MovieDetails

@Dao
abstract class MovieDetailsDao : BaseDao<MovieDetails> {
    @Query("SELECT * FROM movie_details")
    abstract fun findAll(): LiveData<List<MovieDetails>>

    @Query("SELECT * FROM movie_details WHERE id = :id")
    abstract fun findById(id: Int): LiveData<MovieDetails>

}