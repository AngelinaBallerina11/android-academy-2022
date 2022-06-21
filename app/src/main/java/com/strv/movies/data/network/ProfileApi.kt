package com.strv.movies.data.network

import com.squareup.moshi.Json
import com.strv.movies.domain.model.Account
import com.strv.movies.domain.model.FavoriteMovieDetail
import com.strv.movies.domain.model.GenreDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {
    @GET("account")
    suspend fun getAccountDetails(): AccountDTO

    @GET("account/{accountId}/favorite/movies")
    suspend fun getFavoriteMovies(@Path("accountId") accountId: Int): FavoriteMovieDetailResponseDTO
}

data class AccountDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String
)

fun AccountDTO.toDomain() = Account(id, name, username)

data class FavoriteMovieDetailResponseDTO(
    val results: List<FavoriteMovieDetailDTO>
)

data class FavoriteMovieDetailDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String
)

fun FavoriteMovieDetailDTO.toDomain() =
    FavoriteMovieDetail(id, title, overview, releaseDate, posterPath)