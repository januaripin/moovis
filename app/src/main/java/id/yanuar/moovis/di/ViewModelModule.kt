package id.yanuar.moovis.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.yanuar.moovis.presentation.factory.ViewModelFactory
import id.yanuar.moovis.presentation.factory.ViewModelKey
import id.yanuar.moovis.presentation.movie.MovieViewModel
import id.yanuar.moovis.presentation.moviedetails.MovieDetailsViewModel
import id.yanuar.moovis.presentation.splash.SplashViewModel

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(movieViewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}