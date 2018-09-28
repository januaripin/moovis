package id.yanuar.moovis.presentation.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import id.yanuar.moovis.R
import id.yanuar.moovis.data.Status
import id.yanuar.moovis.presentation.movie.MovieActivity
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]

        viewModel.loadAllGenres(Locale.US).observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    Timber.i("success")
                    val i = Intent(this, MovieActivity::class.java)
                    startActivity(i)
                    finish()
                }
                Status.ERROR -> Timber.e(it.throwable)
                else -> Timber.i("loading...")
            }
        })
    }
}
