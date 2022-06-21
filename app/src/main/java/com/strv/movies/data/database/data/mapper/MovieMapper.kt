package com.strv.movies.data.database.data.mapper

import com.strv.movies.domain.model.Movie
import com.strv.movies.domain.model.MovieDTO
import javax.inject.Inject

// Convention is to name a mapper after class of target object.
class MovieMapper @Inject constructor() : Mapper<MovieDTO, Movie> {
    override fun map(from: MovieDTO) =
        Movie(
            id = from.id,
            title = from.title,
            posterPath = from.posterPath,
        )
}