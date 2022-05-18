package com.strv.movies.data

import com.strv.movies.model.MovieDetailDto
import com.strv.movies.model.MovieListResponseDto
import com.strv.movies.model.VideosDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("movie/popular")
    suspend fun getMovieList(): MovieListResponseDto

    @GET("movie/{movieId}/videos")
    suspend fun getTrailer(@Path("movieId") movieId: Int): VideosDto
}
