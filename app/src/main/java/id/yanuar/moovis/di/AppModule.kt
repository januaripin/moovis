package id.yanuar.moovis.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import id.yanuar.moovis.App
import id.yanuar.moovis.presentation.movie.MovieActivity
import id.yanuar.moovis.presentation.movie.MovieFragment
import id.yanuar.moovis.presentation.moviedetails.MovieDetailsActivity
import id.yanuar.moovis.presentation.splash.SplashActivity

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Module(includes = [AndroidSupportInjectionModule::class, ViewModelModule::class])
abstract class AppModule(val app: App) {
    @ContributesAndroidInjector
    abstract fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    abstract fun movieActivityInjector(): MovieActivity

    @ContributesAndroidInjector
    abstract fun movieFragmentInjector(): MovieFragment

    @ContributesAndroidInjector
    abstract fun movieDetailsActivityInjector(): MovieDetailsActivity

}