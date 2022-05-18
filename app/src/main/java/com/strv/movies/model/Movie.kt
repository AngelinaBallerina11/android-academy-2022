package com.strv.movies.model

import com.squareup.moshi.Json

data class MovieListResponseDto(
    val results: List<MovieDto>
)

data class MovieDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String
) {
    fun toDomain() = Movie(id, posterPath, title)
}

data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String
)