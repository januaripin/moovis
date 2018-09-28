package id.yanuar.moovis.data.local.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.yanuar.moovis.data.local.entity.Crew
import java.util.*

class ListOfCrewConverters {
    var gson = Gson()

    @TypeConverter
    fun fromList(value: List<Crew>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<Crew> {
        if (value == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Crew>>() {
        }.type

        return gson.fromJson(value, listType)
    }
}