package id.yanuar.moovis.presentation.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import id.yanuar.moovis.R
import id.yanuar.moovis.data.Status
import id.yanuar.moovis.presentation.moviedetails.MovieDetailsActivity
import id.yanuar.moovis.util.MoviePagerTransformer
import id.yanuar.moovis.util.toPx
import kotlinx.android.synthetic.main.fragment_movie.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val ARG_GROUP = "group_id"

class MovieFragment : Fragment(), MovieAdapter.OnDetailsClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MovieViewModel

    private val locale = Locale.getDefault()

    private val movieAdapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MovieViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpMovies.setPageTransformer(false, MoviePagerTransformer(180f.toPx(activity)))

        arguments?.let {
            when (it.getInt(ARG_GROUP)) {
                0 -> viewModel.setLoadMoviesParams("now_playing", locale.language, locale.country)
                1 -> viewModel.setLoadMoviesParams("upcoming", locale.language, locale.country)
                2 -> viewModel.setLoadMoviesParams("popular", locale.language, locale.country)
                3 -> viewModel.setLoadMoviesParams("top_rated", locale.language, locale.country)
            }
        }

        viewModel.movies.observe(this, Observer {
            it?.run {
                when (status) {
                    Status.SUCCESS -> {
                        Timber.i(data?.toString())
                        vpMovies.adapter = movieAdapter
                        data?.let { movieAdapter.addMovies(it.toMutableList()) }
                    }
                    Status.ERROR -> Timber.e(throwable)
                    else -> Timber.i("loading movies...")
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetailsClick(id: Int, title: String) {
        val i = Intent(activity, MovieDetailsActivity::class.java)
        i.putExtra("id", id)
        i.putExtra("title", title)
        startActivity(i)
    }

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int) =
                MovieFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_GROUP, groupId)
                    }
                }
    }
}
