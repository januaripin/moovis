package id.yanuar.moovis.data.local.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: Collection<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Update
    fun update(data: T)

    @Delete
    fun delete(data: T)
}