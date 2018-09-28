package id.yanuar.moovis.presentation.movie

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.yanuar.moovis.R
import id.yanuar.moovis.data.local.entity.Movie
import id.yanuar.moovis.util.inflate
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val listener: OnDetailsClickListener?) : PagerAdapter() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount() = movies.size - 1

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val movie = movies[position]
        val view = container.inflate(R.layout.item_movie)

        with(view) {
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .into(imagePoster)

            tvTitle.text = movie.title
            tvGenres.text = movie.genres.joinToString(separator = " | ")
            btnDetails.setOnClickListener { listener?.onDetailsClick(movie.id, movie.title) }
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }


    fun addMovies(movies: MutableList<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    interface OnDetailsClickListener {
        fun onDetailsClick(id: Int, title: String)
    }
}