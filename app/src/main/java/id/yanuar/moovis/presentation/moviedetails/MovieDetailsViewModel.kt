package id.yanuar.moovis.presentation.moviedetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.repository.MovieRepository
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(movieRepository: MovieRepository) : ViewModel() {
    private val movieId = MutableLiveData<Int>()

    val movie: LiveData<Resource<MovieDetails>> = Transformations
            .switchMap(movieId, movieRepository::getMovie)

    fun loadMovieById(id: Int) {
        if (movieId.value == id) {
            return
        }

        movieId.value = id
    }

    fun retry() {
        movieId.value?.let {
            movieId.value = it
        }
    }
}