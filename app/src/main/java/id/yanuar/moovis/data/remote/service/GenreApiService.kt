package id.yanuar.moovis.data.remote.service

import android.arch.lifecycle.LiveData
import id.yanuar.moovis.data.remote.response.ApiResponse
import id.yanuar.moovis.data.remote.response.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

interface GenreApiService {

    @GET("genre/{type}/list")
    fun getGenres(@Path("type") type: String, @Query("language") language: String): LiveData<ApiResponse<GenreResponse>>

}