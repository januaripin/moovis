package id.yanuar.moovis.data.remote

import android.arch.core.executor.testing.InstantTaskExecutorRule
import id.yanuar.moovis.LiveDataTestUtil.getValue
import id.yanuar.moovis.data.ServiceTest
import id.yanuar.moovis.data.remote.response.SuccessResponse
import id.yanuar.moovis.data.remote.service.ApiService
import id.yanuar.moovis.data.remote.service.MovieApiService
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

class MovieApiServiceTest : ServiceTest() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var apiService: MovieApiService

    private val locale = Locale.US

    @Before
    override fun setUp() {
        super.setUp()

        apiService = ApiService.build(mockWebServer.url("/"))
                .create(MovieApiService::class.java)
    }

    @Test
    fun callGetMoviesThenReturnMovies() {
        val response = getValue(apiService.getMovies("now_playing", locale.language, locale.country, 1))
        val movies = (response as SuccessResponse).body.results

        val request = mockWebServer.takeRequest()
        assertThat(request.path, containsString("movie/now_playing"))

        assertThat(movies, notNullValue())
        assertThat(movies.size, `is`(20))

        val firstMovie = movies[0]
        assertThat(firstMovie.id, `is`(351286))
        assertThat(firstMovie.title, `is`("Jurassic World : Fallen Kingdom"))

        val secondMovie = movies[1]
        assertThat(secondMovie.id, `is`(363088))
        assertThat(secondMovie.title, `is`("Ant-Man et la Guêpe"))
    }

    @Test
    fun callGetMovieByIdThenReturnMovie() {
        val response = getValue(apiService.getMovie(19995, "credits"))
        val movie = (response as SuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, containsString("movie/19995"))

        assertThat(movie, notNullValue())
        assertThat(movie.id, `is`(19995))
        assertThat(movie.title, `is`("Avatar"))
    }
}