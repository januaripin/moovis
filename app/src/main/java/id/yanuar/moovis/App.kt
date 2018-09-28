package id.yanuar.moovis

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import id.yanuar.moovis.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        when {
            BuildConfig.DEBUG -> Timber.plant(Timber.DebugTree())
        }
        DaggerAppComponent.builder().create(this).inject(this)
    }

    override fun activityInjector() = activityInjector
}