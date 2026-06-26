package com.adam0006.cinelist.network

import com.adam0006.cinelist.BuildConfig
import com.adam0006.cinelist.model.TmdbResponse // sesuaikan model
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

private val tmdbClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }
    .build()

private val tmdbRetrofit = Retrofit.Builder()
    .baseUrl(TMDB_BASE_URL)
    .client(tmdbClient)
    .addConverterFactory(MoshiConverterFactory.create(
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    ))
    .build()

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TmdbResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TmdbResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US"
    ): TmdbResponse
}

object TmdbApi {
    val service: TmdbApiService by lazy {
        tmdbRetrofit.create(TmdbApiService::class.java)
    }
}