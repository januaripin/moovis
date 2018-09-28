package id.yanuar.moovis.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.yanuar.moovis.data.local.entity.MovieGenres
import java.util.*

class ListOfMovieGenresConverters {
    var gson = Gson()

    @TypeConverter
    fun fromList(value: List<MovieGenres>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<MovieGenres> {
        if (value == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<MovieGenres>>() {
        }.type

        return gson.fromJson(value, listType)
    }
}