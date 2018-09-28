package id.yanuar.moovis.data.remote

import android.arch.core.executor.testing.InstantTaskExecutorRule
import id.yanuar.moovis.LiveDataTestUtil.getValue
import id.yanuar.moovis.data.ServiceTest
import id.yanuar.moovis.data.remote.response.SuccessResponse
import id.yanuar.moovis.data.remote.service.ApiService
import id.yanuar.moovis.data.remote.service.GenreApiService
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

class GenreApiServiceTest : ServiceTest() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var genreApiService: GenreApiService

    @Before
    override fun setUp() {
        super.setUp()

        genreApiService = ApiService.build(mockWebServer.url("/"))
                .create(GenreApiService::class.java)
    }

    @Test
    fun callGetMovieGenresThenReturnGenresForMovies() {
        val response = getValue(genreApiService.getGenres("movie", Locale.US.language))
        val genres = (response as SuccessResponse).body.genres

        val request = mockWebServer.takeRequest()
        assertThat(request.path, containsString("genre/movie/list"))

        assertThat(genres, notNullValue())
        assertThat(genres.size, `is`(19))

        val firstGenre = genres[0]
        assertThat(firstGenre.id, `is`(28))
        assertThat(firstGenre.name, `is`("Action"))

        val secondGenre = genres[1]
        assertThat(secondGenre.id, `is`(12))
        assertThat(secondGenre.name, `is`("Adventure"))
    }

    @Test
    fun callGetTVGenresThenReturnGenresForTV() {
        val response = getValue(genreApiService.getGenres("tv", Locale.US.language))
        val genres = (response as SuccessResponse).body.genres

        val request = mockWebServer.takeRequest()
        assertThat(request.path, containsString("genre/tv/list"))

        assertThat(genres, notNullValue())
        assertThat(genres.size, `is`(16))

        val firstGenre = genres[0]
        assertThat(firstGenre.id, `is`(10759))
        assertThat(firstGenre.name, `is`("Action & Adventure"))

        val secondGenre = genres[1]
        assertThat(secondGenre.id, `is`(16))
        assertThat(secondGenre.name, `is`("Animation"))
    }
}