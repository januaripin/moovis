package id.yanuar.moovis.data.local.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.yanuar.moovis.data.local.converter.ListOfCastConverters
import id.yanuar.moovis.data.local.converter.ListOfCrewConverters
import id.yanuar.moovis.data.local.converter.ListOfMovieGenresConverters

data class Credits(var cast: List<Cast>? = emptyList(),
                   var crew: List<Crew>? = emptyList())


data class MovieGenres(var name: String? = "",
                       var id: Int = 0)


data class Crew(var gender: Int = 0,
                var creditId: String? = "",
                var name: String? = "",
                var profilePath: String? = "",
                var id: Int = 0,
                var department: String? = "",
                var job: String? = "")


data class Cast(var castId: Int = 0,
                var character: String? = "",
                var gender: Int = 0,
                var creditId: String? = "",
                var name: String? = "",
                var profilePath: String? = "",
                var id: Int = 0,
                var order: Int = 0)


@Entity(tableName = "movie_details")
@TypeConverters(value = [ListOfCastConverters::class, ListOfCrewConverters::class,
    ListOfMovieGenresConverters::class])
data class MovieDetails(@PrimaryKey
                        var id: Int = 0,
                        var originalLanguage: String? = "",
                        var imdbId: String? = "",
                        var video: Boolean = false,
                        var title: String? = "",
                        var backdropPath: String? = "",
                        var revenue: Long = 0,
                        @Embedded
                        var credits: Credits,
                        var genres: List<MovieGenres>? = emptyList(),
                        var popularity: Double = 0.0,
                        var voteCount: Int = 0,
                        var budget: Int = 0,
                        var overview: String? = "",
                        var originalTitle: String? = "",
                        var runtime: Int = 0,
                        var posterPath: String? = "",
                        var releaseDate: String? = "",
                        var voteAverage: Double = 0.0,
                        var tagline: String? = "",
                        var adult: Boolean = false,
                        var homepage: String? = "",
                        var status: String? = "")


