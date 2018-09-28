package id.yanuar.moovis.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import id.yanuar.moovis.data.local.entity.Genre

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Dao
abstract class GenreDao : BaseDao<Genre> {
    @Query("SELECT * FROM genre")
    abstract fun findAll(): List<Genre>

    @Query("SELECT * FROM genre WHERE id = :id LIMIT 1")
    abstract fun findById(id: Int): LiveData<Genre>

    @Query("SELECT name FROM genre WHERE id = :id LIMIT 1")
    abstract fun findNameById(id: Int): List<String>

    @Query("SELECT * FROM genre WHERE type = :type")
    abstract fun findByType(type: String): LiveData<List<Genre>>
}