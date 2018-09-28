package id.yanuar.moovis.di

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import id.yanuar.moovis.App
import id.yanuar.moovis.data.local.AppDatabase
import id.yanuar.moovis.data.local.dao.GenreDao
import id.yanuar.moovis.data.local.dao.MovieDao
import id.yanuar.moovis.data.local.dao.MovieDetailsDao
import id.yanuar.moovis.data.remote.service.GenreApiService
import id.yanuar.moovis.data.remote.service.MovieApiService
import id.yanuar.moovis.data.repository.GenreRepository
import id.yanuar.moovis.data.repository.MovieRepository
import id.yanuar.moovis.executor.AppExecutors
import javax.inject.Singleton


/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: App): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "movies.db")
                .build()
    }

    @Provides
    @Singleton
    fun provideGenreDao(appDatabase: AppDatabase) = appDatabase.genreDao()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideMovieDetailsDao(appDatabase: AppDatabase) = appDatabase.movieDetailsDao()

    @Provides
    @Singleton
    fun provideGenreRepository(appExecutors: AppExecutors, genreDao: GenreDao, genreApiService: GenreApiService) =
            GenreRepository(appExecutors, genreDao, genreApiService)

    @Provides
    @Singleton
    fun provideMovieRepository(appExecutors: AppExecutors, movieApiService: MovieApiService,
                               movieDao: MovieDao, movieDetailsDao: MovieDetailsDao, genreDao: GenreDao) =
            MovieRepository(appExecutors, movieApiService, movieDao, movieDetailsDao, genreDao)


}