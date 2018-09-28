package id.yanuar.moovis.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Entity(tableName = "genre")
data class Genre(@PrimaryKey val id: Int, var name: String? = "", var type: String)