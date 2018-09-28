package id.yanuar.moovis

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
import id.yanuar.moovis.data.local.entity.Credits
import id.yanuar.moovis.data.local.entity.Genre
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.remote.response.GenreResponse
import id.yanuar.moovis.data.remote.response.MovieListResponse
import id.yanuar.moovis.data.remote.response.MovieListResult

object DataTestUtil {
    fun createGenreResponse(genres: List<Genre>) = GenreResponse(genres)

    fun createGenre(id: Int, name: String, type: String) = Genre(id, name, type)

    fun createGenres(count: Int, name: String, type: String) = (1..count).map {
        createGenre(it, name + it, type)
    }

    fun createMovieListResponse(count: Int, title: String) = MovieListResponse((1..count).map {
        MovieListResult().apply {
            this.id = it
            this.title = title + it
        }
    })

    fun createMovies(count: Int, title: String, inGroup: String) = (1..count).map {
        Movie().apply {
            this.id = it
            this.title = title + it
            when (inGroup) {
                "now" -> this.nowPlaying = 1
                "popular" -> this.nowPlaying = 1
                "top" -> this.nowPlaying = 1
                "upcoming" -> this.nowPlaying = 1
            }
        }
    }

    fun createMovieDetails() = MovieDetails(credits = Credits()).apply {
        id = 19995
        title = "Avatar"
    }
}