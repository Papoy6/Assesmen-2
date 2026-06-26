package com.adam0006.cinelist.network

import com.adam0006.cinelist.model.Film
import com.adam0006.cinelist.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "http://192.168.1.10/cinelist-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CinelistApiService {
    @GET("film.php")
    suspend fun getFilm(
        @Header("Authorization") userId: String
    ): List<Film>

    @Multipart
    @POST("film.php")
    suspend fun postFilm(
        @Header("Authorization") userId: String,
        @Part("judul") judul: RequestBody,
        @Part("genre") genre: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("film.php")
    suspend fun deleteFilm(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object CinelistApi {
    val service: CinelistApiService by lazy {
        retrofit.create(CinelistApiService::class.java)
    }

    fun getFilmPosterUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }