package id.yanuar.moovis.presentation.moviedetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import id.yanuar.moovis.R
import id.yanuar.moovis.data.Status
import id.yanuar.moovis.data.local.entity.Cast
import id.yanuar.moovis.data.local.entity.Crew
import id.yanuar.moovis.data.local.entity.MovieDetails
import kotlinx.android.synthetic.main.activity_movie_details.*
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity(), CastAdapter.OnCastClickListener, CrewAdapter.OnCrewClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieDetailsViewModel

    private val castAdapter = CastAdapter(this)
    private val crewAdapter = CrewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra("title")

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MovieDetailsViewModel::class.java]

        viewModel.loadMovieById(intent.getIntExtra("id", 0))
        viewModel.movie.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> it.data?.let(this::showMovie)
                Status.ERROR -> {
                    Timber.e(it.throwable)
                }
                else -> {

                }
            }
        })
        rvCasts.layoutManager = GridLayoutManager(this, 2, HORIZONTAL, false)
        rvCasts.setHasFixedSize(true)
        rvCasts.adapter = castAdapter

        rvCrews.layoutManager = GridLayoutManager(this, 2, HORIZONTAL, false)
        rvCrews.setHasFixedSize(true)
        rvCrews.adapter = crewAdapter
    }

    override fun onCastClick(item: Cast) {

    }

    override fun onCrewClick(item: Crew) {

    }

    private fun showMovie(movie: MovieDetails) = with(movie) {
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original/$backdropPath")
                .into(imageBackdrop)

        Picasso.get()
                .load("https://image.tmdb.org/t/p/original/$posterPath")
                .into(imagePoster)

        tvTitle.text = title
        tvOverview.text = overview
        tvGenres.text = genres?.joinToString(" | ") {
            it.name ?: ""
        }

        credits.cast?.let {
            castAdapter.data = it.toMutableList()
        }
        credits.crew?.let {
            Timber.i(it.toString())
            crewAdapter.data = it.toMutableList()
        }

    }
}
