package id.yanuar.moovis.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

class ListOfStringConverters {
    var gson = Gson()

    @TypeConverter
    fun fromList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<String> {
        if (value == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<String>>() {
        }.type

        return gson.fromJson(value, listType)
    }
}