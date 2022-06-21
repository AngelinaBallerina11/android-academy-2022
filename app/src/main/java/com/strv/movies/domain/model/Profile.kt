package com.strv.movies.domain.model

data class Account(
    val id: Int,
    val name: String,
    val username: String
)

data class FavoriteMovieDetail(
    val id: Int,
    val title: String,
    val overview: String?,
    val releaseDate: String,
    val posterPath: String
)

fun FavoriteMovieDetail.toMovie() = Movie(id, title, posterPath)