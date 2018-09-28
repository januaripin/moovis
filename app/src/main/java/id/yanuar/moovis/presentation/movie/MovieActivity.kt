package id.yanuar.moovis.presentation.movie

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import id.yanuar.moovis.R
import kotlinx.android.synthetic.main.activity_movie.*
import javax.inject.Inject

class MovieActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        tabMovies.addTab(tabMovies.newTab().setText("NOW PLAYING"))
        tabMovies.addTab(tabMovies.newTab().setText("COMING SOON"))
        tabMovies.addTab(tabMovies.newTab().setText("POPULAR"))
        tabMovies.addTab(tabMovies.newTab().setText("TOP RATED"))

        tabMovies.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.layoutContainer, MovieFragment.newInstance(tab?.position
                                ?: 0))
                        .commit()
            }
        })

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.layoutContainer, MovieFragment.newInstance(0))
                .commit()

    }
}
