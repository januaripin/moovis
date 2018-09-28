package id.yanuar.moovis.data.remote.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import id.yanuar.moovis.BuildConfig
import id.yanuar.moovis.data.remote.adapter.LiveDataCallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    @JvmStatic
    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @JvmStatic
    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        when {
            BuildConfig.DEBUG -> builder.addInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        builder.addInterceptor {
            val original = it.request()
            val originalUrl = original.url()

            val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

            val request = original.newBuilder()
                    .url(url)
                    .build()

            it.proceed(request)
        }

        return builder.build()
    }

    @JvmStatic
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun build(httpUrl: HttpUrl?) = Retrofit.Builder()
            .baseUrl(httpUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient())
            .build()
}