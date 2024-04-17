package com.freecast.thatmovieapp.domain.usecase

import com.freecast.thatmovieapp.domain.core.BaseUseCase
import com.freecast.thatmovieapp.domain.model.Genre
import com.freecast.thatmovieapp.domain.repository.MovieRepository
import com.freecast.thatmovieapp.domain.repository.Resource
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(private val movieRepository: MovieRepository) : BaseUseCase<Flow<Resource<List<Genre>>>, Unit>() {
    override suspend fun execute(params: Unit):
            Flow<Resource<List<Genre>>> {
        return movieRepository.getGenres()
    }
}