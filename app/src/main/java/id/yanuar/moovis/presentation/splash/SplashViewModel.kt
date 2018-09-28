package id.yanuar.moovis.presentation.splash

import android.arch.lifecycle.ViewModel
import id.yanuar.moovis.data.repository.GenreRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class SplashViewModel @Inject constructor(private val repository: GenreRepository) : ViewModel() {
    fun loadAllGenres(locale: Locale) = repository.getAllGenres(locale)
}