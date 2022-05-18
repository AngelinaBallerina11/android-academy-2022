package com.strv.movies.model

data class Trailer(
    val key: String
)

data class TrailerDto(
    val key: String
) {
    fun toDomain() = Trailer(key)
}

data class VideosDto(
    val results: List<TrailerDto>
)