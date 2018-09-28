package id.yanuar.moovis.presentation.splash

import android.arch.core.executor.testing.InstantTaskExecutorRule
import id.yanuar.moovis.data.repository.GenreRepository
import id.yanuar.moovis.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@RunWith(JUnit4::class)
class SplashViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val genreRepository = mock<GenreRepository>()
    private val viewModel = SplashViewModel(genreRepository)

    @Test
    fun testHasOrGetGenres() {
        viewModel.loadAllGenres(Locale.US)
    }
}