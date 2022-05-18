package com.strv.movies.model

import com.squareup.moshi.Json

data class MovieDetailDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "runtime")
    val runtime: Int
) {
    fun toDomain() = MovieDetail(
        id,
        title,
        overview,
        releaseDate.substringBefore("-"),
        posterPath,
        revenue,
        runtime
    )
}

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseYear: String,
    val posterPath: String,
    val revenue: Int,
    val runtime: Int
)

data class Collection(
    val id: Int,
    val name: String,
    val overview: String?,
    val posterPath: String,
    val backdropPath: String,
)

data class Genre(
    val id: Int,
    val name: String,
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String,
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String,
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String,
)