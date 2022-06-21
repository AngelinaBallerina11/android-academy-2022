package com.strv.movies.data

import androidx.room.withTransaction
import com.strv.movies.data.database.data.dao.MoviesDao
import com.strv.movies.data.database.data.entity.toDomain
import com.strv.movies.data.database.data.mapper.MovieMapper
import com.strv.movies.data.database.MoviesDatabase
import com.strv.movies.data.network.MovieApi
import com.strv.movies.domain.Either
import com.strv.movies.domain.model.Movie
import com.strv.movies.domain.model.MovieDetail
import com.strv.movies.domain.model.MovieDetailDTO
import com.strv.movies.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val moviesDao: MoviesDao,
    private val movieMapper: MovieMapper,
    private val moviesDatabase: MoviesDatabase
) {

    suspend fun fetchMovieDetail(movieId: Int): Either<String, String> {
        return try {
            val movie = api.getMovieDetail(movieId)
            storeMovieDetail(movie)
            Either.Value(movie.title)
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }

    suspend fun getPopularMovies(): Either<String, List<Movie>> {
        return try {
            val popularMovies = api.getPopularMovies()
            Either.Value(popularMovies.results.map { movieMapper.map(it) })
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }

    fun observeMovieDetail(movieId: Int): Flow<MovieDetail?> =
        moviesDao.observeMovieDetail(movieId).map {
            it?.toDomain()
        }

    private suspend fun storeMovieDetail(movie: MovieDetailDTO) {
        moviesDatabase.withTransaction {
            moviesDao.insertMovieDetail(movie.toEntity())
            moviesDao.insertGenres(movie.genres.map { it.toEntity() })
            moviesDao.insertMovieGenres(movie.genres.map { it.toEntity(movie.id) })
        }
    }
}
