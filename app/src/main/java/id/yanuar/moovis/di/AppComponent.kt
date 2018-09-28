package id.yanuar.moovis.di

import dagger.Component
import dagger.android.AndroidInjector
import id.yanuar.moovis.App
import javax.inject.Singleton

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}