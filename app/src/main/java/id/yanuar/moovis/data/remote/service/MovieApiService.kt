package id.yanuar.moovis.data.remote.service

import android.arch.lifecycle.LiveData
import id.yanuar.moovis.data.local.entity.MovieDetails
import id.yanuar.moovis.data.remote.response.ApiResponse
import id.yanuar.moovis.data.remote.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

interface MovieApiService {
    @GET("movie/{group}")
    fun getMovies(@Path("group") group: String, @Query("language") language: String?,
                  @Query("region") region: String?, @Query("page") page: Int): LiveData<ApiResponse<MovieListResponse>>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Int, @Query("append_to_response") appendToResponse: String): LiveData<ApiResponse<MovieDetails>>
}