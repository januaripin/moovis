package id.yanuar.moovis.presentation.moviedetails

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import id.yanuar.moovis.DataTestUtil
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.repository.MovieRepository
import id.yanuar.moovis.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*

class MovieDetailsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<MovieRepository>()
    private val viewModel = MovieDetailsViewModel(repository)

    private val movieId = 19995

    @Test
    fun testNull() {
        MatcherAssert.assertThat(viewModel.movie, CoreMatchers.notNullValue())
        Mockito.verify(repository, Mockito.never()).getMovie(anyInt())
    }

    @Test
    fun testLoadMovieByIdWithoutObservers() {
        viewModel.loadMovieById(movieId)
        Mockito.verify(repository, Mockito.never()).getMovie(movieId)
    }

    @Test
    fun testLoadMovieByIdWhenObserved() {
        viewModel.loadMovieById(movieId)
        viewModel.movie.observeForever(mock())
        Mockito.verify(repository).getMovie(movieId)
    }

    @Test
    fun testRetryLoadMovieById() {
        viewModel.retry()
        Mockito.verifyNoMoreInteractions(repository)

        viewModel.loadMovieById(movieId)
        Mockito.verifyNoMoreInteractions(repository)

        val observer = mock<Observer<Resource<MovieDetails>>>()
        viewModel.movie.observeForever(observer)
        Mockito.verify(repository).getMovie(movieId)

        Mockito.reset(repository)

        viewModel.retry()
        viewModel.loadMovieById(movieId)
    }

    @Test
    fun testLoadMovieByIdThenSendToUI() {
        val movieDetails = MutableLiveData<Resource<MovieDetails>>()
        Mockito.`when`(repository.getMovie(movieId)).thenReturn(movieDetails)

        val observer = mock<Observer<Resource<MovieDetails>>>()
        viewModel.movie.observeForever(observer)
        viewModel.loadMovieById(movieId)
        Mockito.verify(observer, never()).onChanged(any())

        val movie = DataTestUtil.createMovieDetails()
        val resMovie = Resource.success(movie)

        movieDetails.value = resMovie
        verify(observer).onChanged(resMovie)
    }
}