package id.yanuar.moovis.presentation.movie

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.repository.MovieRepository
import id.yanuar.moovis.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.util.*

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(JUnit4::class)
class MovieViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<MovieRepository>()
    private val viewModel = MovieViewModel(repository)
    private val locale = Locale.US
    @Test
    fun testNull() {
        assertThat(viewModel.movies, notNullValue())
        verify(repository, never()).getMovies(anyString(), anyString(), anyString())
        viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
        verify(repository, never()).getMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun testLoadMoviesWithoutObservers() {
        viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
        verify(repository, never()).getMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun testLoadMoviesWhenObserved() {
        viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
        viewModel.movies.observeForever(mock())
        verify(repository).getMovies(anyString(), anyString(), anyString())
    }

    @Test
    fun testRetryLoadMovies() {
        viewModel.retry()
        verifyNoMoreInteractions(repository)

        viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
        verifyNoMoreInteractions(repository)

        val observer = mock<Observer<Resource<List<Movie>>>>()
        viewModel.movies.observeForever(observer)
        verify(repository).getMovies("now_playing", locale.language, locale.country)

        reset(repository)

        viewModel.retry()
        viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
    }

    @Test
    fun testLoadMoviesParams() {
        viewModel.setLoadMoviesParams("", locale.language, locale.country)
        val observer = mock<Observer<Resource<List<Movie>>>>()
        viewModel.movies.observeForever(observer)
        verify(observer).onChanged(null)
    }
}