package id.yanuar.moovis.di

import dagger.Module
import dagger.Provides
import id.yanuar.moovis.data.remote.service.ApiService
import id.yanuar.moovis.data.remote.service.GenreApiService
import id.yanuar.moovis.data.remote.service.MovieApiService
import okhttp3.HttpUrl
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit() = ApiService.build(HttpUrl.parse("https://api.themoviedb.org/3/"))

    @Singleton
    @Provides
    fun provideGenreApiService(retrofit: Retrofit) = retrofit.create(GenreApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit) = retrofit.create(MovieApiService::class.java)


}