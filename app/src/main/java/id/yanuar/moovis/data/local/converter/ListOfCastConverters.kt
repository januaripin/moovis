package id.yanuar.moovis.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.yanuar.moovis.data.local.entity.Cast
import java.util.*

class ListOfCastConverters {
    var gson = Gson()

    @TypeConverter
    fun fromList(value: List<Cast>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<Cast> {
        if (value == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Cast>>() {
        }.type

        return gson.fromJson(value, listType)
    }
}