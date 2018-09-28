package id.yanuar.moovis.data.repository

import android.arch.lifecycle.LiveData
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.dao.MovieDao
import id.yanuar.moovis.data.local.dao.MovieDetailsDao
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.remote.response.MovieListResponse
import id.yanuar.moovis.data.remote.service.MovieApiService
import id.yanuar.moovis.executor.AppExecutors
import java.util.*
import javax.inject.Inject

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class MovieRepository @Inject constructor(private val appExecutors: AppExecutors,
                                          private val apiService: MovieApiService,
                                          private val movieDao: MovieDao,
                                          private val detailsDao: MovieDetailsDao,
                                          private val genreDao: GenreDao) {

    fun getMovies(group: String, language: String, country: String): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MovieListResponse>(appExecutors) {
            override fun saveCallResult(item: MovieListResponse) = with(item.results) {
                val genres = genreDao.findAll()
                val movies = mutableListOf<Movie>()
                for (result in this) {
                    movies.add(Movie().apply {
                        id = result.id
                        voteCount = result.voteCount
                        voteAverage = result.voteAverage
                        backdropPath = result.backdropPath.orEmpty()
                        posterPath = result.posterPath
                        title = result.title
                        adult = result.adult
                        originalTitle = result.originalTitle
                        originalLanguage = result.originalLanguage
                        video = result.video
                        popularity = result.popularity
                        releaseDate = result.releaseDate
                        overview = result.overview

                        when (group) {
                            "now_playing" -> nowPlaying = 1
                            "top_rated" -> topRated = 1
                            "popular" -> popular = 1
                            "upcoming" -> upcoming = 1
                        }

                        this.genres = genres.filter { result.genreIds.contains(it.id) }.map {
                            it.name ?: ""
                        }
                    })
                }

                movieDao.insertAll(movies)
            }

            override fun shouldFetch(data: List<Movie>?) = data == null || data.isEmpty()

            override fun loadFromDb() = when (group) {
                "now_playing" -> movieDao.findNowPlayingMovies()
                "top_rated" -> movieDao.findTopRatedMovies()
                "popular" -> movieDao.findPopularMovies()
                else -> movieDao.findUpcomingMovies()
            }

            override fun createCall() = apiService.getMovies(group, language, country, 1)
        }.asLiveData()
    }

    fun getMovie(id: Int): LiveData<Resource<MovieDetails>> {
        return object : NetworkBoundResource<MovieDetails, MovieDetails>(appExecutors) {
            override fun saveCallResult(item: MovieDetails) = detailsDao.insert(item)

            override fun shouldFetch(data: MovieDetails?) = data == null

            override fun loadFromDb() = detailsDao.findById(id)

            override fun createCall() = apiService.getMovie(id, "credits")
        }.asLiveData()
    }
}