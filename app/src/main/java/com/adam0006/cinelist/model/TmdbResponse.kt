package com.adam0006.cinelist.model

import com.squareup.moshi.Json

data class TmdbResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<TmdbMovie>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

data class TmdbMovie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>
)