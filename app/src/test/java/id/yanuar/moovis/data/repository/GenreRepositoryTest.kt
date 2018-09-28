package id.yanuar.moovis.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import id.yanuar.moovis.DataTestUtil.createGenreResponse
import id.yanuar.moovis.DataTestUtil.createGenres
import id.yanuar.moovis.data.Resource
import id.yanuar.moovis.data.local.AppDatabase
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.entity.Genre
import id.yanuar.moovis.data.remote.service.GenreApiService
import id.yanuar.moovis.mock
import id.yanuar.moovis.util.ApiUtil.successCall
import id.yanuar.moovis.util.InstantAppExecutors
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.util.*

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(JUnit4::class)
class GenreRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service = mock(GenreApiService::class.java)
    private val dao = mock(GenreDao::class.java)

    lateinit var genreRepository: GenreRepository

    @Before
    fun setUp() {
        val db = mock(AppDatabase::class.java)
        `when`(db.genreDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()

        genreRepository = GenreRepository(InstantAppExecutors(), dao, service)
    }

    @Test
    fun getGenreFromNetwork() {
        val locale = Locale.US

        val fromDb = MutableLiveData<List<Genre>>()
        `when`(dao.findByType("movie")).thenReturn(fromDb)

        val genres = createGenres(2, "genre", "movie")
        val genreResponse = createGenreResponse(genres)
        val call = successCall(genreResponse)
        `when`(service.getGenres("movie", locale.language)).thenReturn(call)

        val data = genreRepository.getGenres("movie", Locale.US)
        verify(dao).findByType("movie")
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Genre>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))
        val updateDb = MutableLiveData<List<Genre>>()
        `when`(dao.findByType("movie")).thenReturn(updateDb)

        fromDb.postValue(listOf())
        verify(service).getGenres("movie", locale.language)
        verify(dao).insertAll(genres)

        updateDb.postValue(genres)
        verify(observer).onChanged(Resource.success(genres))
    }
}