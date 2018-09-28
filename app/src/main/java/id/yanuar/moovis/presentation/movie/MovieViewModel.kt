package id.yanuar.moovis.presentation.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.repository.MovieRepository
import id.yanuar.moovis.util.EmptyLiveData
import javax.inject.Inject

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private val loadMoviesParams = MutableLiveData<LoadMoviesParams>()

    val movies: LiveData<Resource<List<Movie>>> = Transformations
            .switchMap(loadMoviesParams) { params ->
                params.ifExists { group, language, country ->
                    movieRepository.getMovies(group, language, country)
                }
            }

    fun setLoadMoviesParams(group: String, language: String, country: String) {
        val update = LoadMoviesParams(group, language, country)
        if (loadMoviesParams.value == update) {
            return
        }

        loadMoviesParams.value = update
    }

    fun retry() {
        loadMoviesParams.value?.reinitialize { group, language, country ->
            loadMoviesParams.value = LoadMoviesParams(group, language, country)
        }
    }

    data class LoadMoviesParams(val group: String, val language: String, val country: String) {
        fun <T> ifExists(f: (String, String, String) -> LiveData<T>): LiveData<T> {
            return if (group.isBlank() || language.isEmpty() || country.isEmpty()) {
                EmptyLiveData.create()
            } else {
                f(group, language, country)
            }
        }

        fun reinitialize(f: (String, String, String) -> Unit) {
            if (group.isNotBlank() && language.isNotEmpty() && country.isNotEmpty()) {
                f(group, language, country)
            }
        }
    }
}