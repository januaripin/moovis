package id.yanuar.moovis.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import id.yanuar.moovis.DataTestUtil
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.AppDatabase
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.dao.MovieDao
import id.yanuar.moovis.data.local.dao.MovieDetailsDao
import id.yanuar.moovis.data.local.entity.Genre
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.remote.service.MovieApiService
import id.yanuar.moovis.mock
import id.yanuar.moovis.util.ApiUtil
import id.yanuar.moovis.util.InstantAppExecutors
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import java.util.*

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(JUnit4::class)
class MovieRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service = Mockito.mock(MovieApiService::class.java)
    private val dao = Mockito.mock(MovieDao::class.java)
    private val genreDao = Mockito.mock(GenreDao::class.java)
    private val movieDetailsDao = Mockito.mock(MovieDetailsDao::class.java)

    lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.movieDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        repository = MovieRepository(InstantAppExecutors(), service, dao, movieDetailsDao, genreDao)
    }

    @Test
    fun testLoadNowPlayingMovies() {
        val locale = Locale.US

        val genreFromDb = listOf<Genre>()
        Mockito.`when`(genreDao.findAll()).thenReturn(genreFromDb)

        val fromDb = MutableLiveData<List<Movie>>()
        Mockito.`when`(dao.findNowPlayingMovies()).thenReturn(fromDb)

        val movies = DataTestUtil.createMovies(5, "movie", "now")
        val movieListResponse = DataTestUtil.createMovieListResponse(5, "movie")
        val call = ApiUtil.successCall(movieListResponse)
        Mockito.`when`(service.getMovies(anyString(), anyString(), anyString(), anyInt())).thenReturn(call)

        val data = repository.getMovies("now_playing", locale.language, locale.country)
        Mockito.verify(dao).findNowPlayingMovies()
        Mockito.verifyNoMoreInteractions(service)
        Mockito.verifyNoMoreInteractions(genreDao)

        val observer = mock<Observer<Resource<List<Movie>>>>()
        data.observeForever(observer)
        Mockito.verifyNoMoreInteractions(service)
        Mockito.verifyNoMoreInteractions(genreDao)
        Mockito.verify(observer).onChanged(Resource.loading(null))
        val updateDb = MutableLiveData<List<Movie>>()
        Mockito.`when`(dao.findNowPlayingMovies()).thenReturn(updateDb)

        fromDb.postValue(listOf())
        Mockito.verify(service).getMovies("now_playing", locale.language, locale.country, 1)
        Mockito.verify(genreDao).findAll()
        Mockito.verify(dao).insertAll(movies)

        updateDb.postValue(movies)
        Mockito.verify(observer).onChanged(Resource.success(movies))
    }

    @Test
    fun testGetMovieWithId19995() {
        val movieId = 19995

        val fromDb = MutableLiveData<MovieDetails>()
        Mockito.`when`(movieDetailsDao.findById(movieId)).thenReturn(fromDb)

        val movie = DataTestUtil.createMovieDetails()
        val call = ApiUtil.successCall(movie)
        Mockito.`when`(service.getMovie(anyInt(), anyString())).thenReturn(call)

        val data = repository.getMovie(movieId)
        Mockito.verify(movieDetailsDao).findById(movieId)
        Mockito.verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<MovieDetails>>>()
        data.observeForever(observer)
        Mockito.verifyNoMoreInteractions(service)
        Mockito.verify(observer).onChanged(Resource.loading(null))

        val updateDb = MutableLiveData<MovieDetails>()
        Mockito.`when`(movieDetailsDao.findById(movieId)).thenReturn(updateDb)

        fromDb.postValue(null)
        Mockito.verify(service).getMovie(movieId, "credits")
        Mockito.verify(movieDetailsDao).insert(movie)

        updateDb.postValue(movie)
        Mockito.verify(observer).onChanged(Resource.success(movie))
    }
}