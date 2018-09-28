package id.yanuar.moovis.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.yanuar.moovis.data.local.converter.ListOfStringConverters

@Entity(tableName = "movie")
@TypeConverters(value = [ListOfStringConverters::class])
data class Movie(
        @PrimaryKey
        var id: Int = 0,
        var voteCount: Int = 0,
        var video: Boolean = false,
        var voteAverage: Double = 0.0,
        var title: String = "",
        var popularity: Double = 0.0,
        var posterPath: String = "",
        var originalLanguage: String = "",
        var originalTitle: String = "",
        var genres: List<String> = emptyList(),
        var backdropPath: String = "",
        var adult: Boolean = false,
        var overview: String = "",
        var releaseDate: String = "",
        var nowPlaying: Int = 0,
        var popular: Int = 0,
        var topRated: Int = 0,
        var upcoming: Int = 0
)