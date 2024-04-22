package com.freecast.thatmovieapp.data.remote


import MovieDetailEntity
import MovieDetailEntityMapper
import com.freecast.thatmovieapp.data.mapper.GenreEntityMapper
import com.freecast.thatmovieapp.data.mapper.MovieEntityMapper
import com.freecast.thatmovieapp.data.mapper.MovieVideoEntityMapper
import com.freecast.thatmovieapp.domain.model.GenreEntity
import com.freecast.thatmovieapp.data.remote.ApiService
import com.freecast.thatmovieapp.data.remote.SafeApiRequest
import com.freecast.thatmovieapp.domain.model.MovieEntity
import com.freecast.thatmovieapp.domain.model.MovieVideoEntity
import com.freecast.thatmovieapp.domain.model.Resource
import com.freecast.thatmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(private val apiService: ApiService) : SafeApiRequest(), MovieRepository {
    private val language = "en-US"
    override suspend fun getGenres(): Flow<Resource<List<GenreEntity>>> {
        return flow {
            emit(Resource.Loading())
            val resource = sendRequest(
                call = { apiService.getGenres(language) },
                mapper = GenreEntityMapper()
            )
            emit(resource)
        }
    }

    override suspend fun getMovies(endpoint: String): Flow<Resource<List<MovieEntity>>> {
        return flow {
            emit(Resource.Loading())
            val resource = sendRequest(
                call = { apiService.getMovies(endpoint) },
                mapper = MovieEntityMapper()
            )
            emit(resource)
        }
    }

    override suspend fun getMoviesByGenreId(genreId: Int): Flow<Resource<List<MovieEntity>>> {
        return flow {
            emit(Resource.Loading())
            val resource = sendRequest(
                call = { apiService.getMoviesByGenre(genreId) },
                mapper = MovieEntityMapper()
            )
            emit(resource)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailEntity>> {
        return flow {
            emit(Resource.Loading())
            val resource = sendRequest(
                call = { apiService.getMovieDetail(movieId) },
                mapper = MovieDetailEntityMapper()
            )
            emit(resource)
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Flow<Resource<List<MovieVideoEntity>>> {
        return flow {
            emit(Resource.Loading())
            val resource = sendRequest(
                call = { apiService.getMovieVideos(movieId) },
                mapper = MovieVideoEntityMapper()
            )
            emit(resource)
        }
    }

}