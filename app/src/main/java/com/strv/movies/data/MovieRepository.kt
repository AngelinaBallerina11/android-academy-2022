package com.strv.movies.data

import com.strv.movies.extension.Either
import com.strv.movies.model.Movie
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.Trailer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi
) {

    suspend fun getMovieDetail(movieId: Int): Either<String, MovieDetail> {
        return try {
            val movieDto = api.getMovieDetail(movieId)
            Either.Value(movieDto.toDomain())
        } catch (e: Throwable) {
            Either.Error(e.localizedMessage ?: "Network error")
        }
    }

    suspend fun getMovieList(): Either<String, List<Movie>> {
        return try {
            val moviesDto = api.getMovieList().results
            Either.Value(moviesDto.map { it.toDomain() })
        } catch (e: Throwable) {
            Either.Error(e.localizedMessage ?: "Network error")
        }
    }

    suspend fun getTrailer(movieId: Int): Either<String, Trailer> {
        return try {
            val trailersDto = api.getTrailer(movieId).results
            Either.Value(trailersDto.first().toDomain())
        } catch (e: Throwable) {
            Either.Error(e.localizedMessage ?: "Network error")
        }
    }
}