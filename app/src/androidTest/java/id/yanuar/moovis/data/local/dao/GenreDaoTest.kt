package id.yanuar.moovis.data.local.dao

import android.support.test.runner.AndroidJUnit4
import id.yanuar.moovis.DataTestUtil.createGenre
import id.yanuar.moovis.DataTestUtil.createGenres
import id.yanuar.moovis.LiveDataTestUtil.getValue
import id.yanuar.moovis.data.local.AppDatabaseTest
import id.yanuar.moovis.data.local.entity.Genre
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(AndroidJUnit4::class)
class GenreDaoTest : AppDatabaseTest() {
    lateinit var genreDao: GenreDao

    @Before
    override fun setUp() {
        super.setUp()
        genreDao = appDatabase.genreDao()
    }

    @Test
    fun testInsertAndFindGenre() {
        genreDao.insert(createGenre(1, "genre", "movie"))

        val genre = getValue(genreDao.findById(1))
        assertThat(genre, notNullValue())
        assertThat(genre.id, `is`(1))
        assertThat(genre.name, `is`("genre"))
    }

    @Test
    fun testInsertGenres() {
        genreDao.insertAll(createGenres(2, "genre", "movie"))

        val genres: List<Genre> = getValue(genreDao.findByType("movie"))

        assertThat(genres, notNullValue())
        assertThat(genres.size, `is`(2))

        val first = genres[0]
        assertThat(first.id, `is`(1))
        assertThat(first.name, `is`("genre1"))
        assertThat(first.type, `is`("movie"))

        val second = genres[1]
        assertThat(second.id, `is`(2))
        assertThat(second.name, `is`("genre2"))
        assertThat(second.type, `is`("movie"))
    }
}