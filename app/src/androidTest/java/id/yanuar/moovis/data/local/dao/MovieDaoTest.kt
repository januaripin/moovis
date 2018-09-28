package id.yanuar.moovis.data.local.dao

import id.yanuar.moovis.DataTestUtil
import id.yanuar.moovis.LiveDataTestUtil
import id.yanuar.moovis.data.local.AppDatabaseTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class MovieDaoTest : AppDatabaseTest() {
    lateinit var dao: MovieDao

    @Before
    override fun setUp() {
        super.setUp()
        dao = appDatabase.movieDao()
    }

    @Test
    fun testInsertMovies() {
        dao.insertAll(DataTestUtil.createMovies(5, "movie", "now"))

        val movies = LiveDataTestUtil.getValue(dao.findNowPlayingMovies())

        MatcherAssert.assertThat(movies, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movies.size, CoreMatchers.`is`(5))

        val first = movies[0]
        MatcherAssert.assertThat(first.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(first.title, CoreMatchers.`is`("movie1"))
        MatcherAssert.assertThat(first.nowPlaying, CoreMatchers.`is`(1))

        val second = movies[1]
        MatcherAssert.assertThat(second.id, CoreMatchers.`is`(2))
        MatcherAssert.assertThat(second.title, CoreMatchers.`is`("movie2"))
        MatcherAssert.assertThat(second.nowPlaying, CoreMatchers.`is`(1))
    }
}