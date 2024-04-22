package com.freecast.thatmovieapp.data.mapper

import com.freecast.thatmovieapp.data.model.MovieModel
import com.freecast.thatmovieapp.data.model.MovieResult
import com.freecast.thatmovieapp.domain.model.MovieEntity


class MovieEntityMapper : (MovieResult) -> List<MovieEntity> {
    fun map(movieModel: MovieModel): MovieEntity {
        return MovieEntity(
            backdropPath = movieModel.backdropPath,
            id = movieModel.id,
            originalTitle = movieModel.originalTitle,
            overview = movieModel.overview,
            posterPath = movieModel.posterPath,
            title = movieModel.title,
            video = movieModel.video
        )
    }

    override fun invoke(movies: MovieResult): List<MovieEntity> {
        return movies.results.map { map(it) }
    }
}