package id.yanuar.moovis.data.remote.response

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

class MovieListResponse(val results: List<MovieListResult>)

class MovieListResult(var id: Int = 0,
                      var voteCount: Int = 0,
                      var video: Boolean = false,
                      var voteAverage: Double = 0.0,
                      var title: String = "",
                      var popularity: Double = 0.0,
                      var posterPath: String = "",
                      var originalLanguage: String = "",
                      var originalTitle: String = "",
                      var genreIds: List<Int> = emptyList(),
                      var backdropPath: String? = "",
                      var adult: Boolean = false,
                      var overview: String = "",
                      var releaseDate: String = "")