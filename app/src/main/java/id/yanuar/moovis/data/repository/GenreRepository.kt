package id.yanuar.moovis.data.repository

import android.arch.lifecycle.LiveData
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.Status
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.entity.Genre
import id.yanuar.moovis.data.remote.response.GenreResponse
import id.yanuar.moovis.data.remote.service.GenreApiService
import id.yanuar.moovis.executor.AppExecutors
import id.yanuar.moovis.util.zip
import java.util.*
import javax.inject.Inject

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

class GenreRepository @Inject constructor(private val appExecutors: AppExecutors,
                                          private val genreDao: GenreDao,
                                          private val genreApiService: GenreApiService) {

    fun getGenres(type: String, locale: Locale): LiveData<Resource<List<Genre>>> {
        return object : NetworkBoundResource<List<Genre>, GenreResponse>(appExecutors) {
            override fun saveCallResult(item: GenreResponse) {
                genreDao.insertAll(item.genres.map { it.copy(type = type) })
            }

            override fun shouldFetch(data: List<Genre>?) = data == null || data.isEmpty()

            override fun loadFromDb() = genreDao.findByType(type)

            override fun createCall() = genreApiService.getGenres(type, locale.language)
        }.asLiveData()
    }

    fun getAllGenres(locale: Locale) = getGenres("movie", locale)
            .zip(getGenres("tv", locale)) { movie, tv ->
                when {
                    movie.status == Status.SUCCESS && tv.status == Status.SUCCESS -> {
                        val genres = mutableListOf<Genre>()
                        genres.addAll(movie.data ?: emptyList())
                        genres.addAll(tv.data ?: emptyList())
                        Resource.success((movie.data ?: emptyList()).size)
                    }
                    else -> Resource.error(movie.throwable ?: tv.throwable)
                }
            }
}