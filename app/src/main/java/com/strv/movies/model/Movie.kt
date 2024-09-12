package com.strv.movies.model

import com.squareup.moshi.Json

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val averageVote: Float
)

data class MovieDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "vote_average")
    val averageVote: Float
)

// We get data in a way that pagination can be implemented later on :)
data class PopularMoviesDTO(
    @Json(name = "results")
    val results: List<MovieDTO>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)