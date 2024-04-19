package com.freecast.thatmovieapp.presentation.detail

import MovieDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freecast.thatmovieapp.core.ui.BaseViewModel
import com.freecast.thatmovieapp.data.remote.exceptions.BaseException
import com.freecast.thatmovieapp.domain.model.MovieVideo
import com.freecast.thatmovieapp.domain.repository.MovieRepository
import com.freecast.thatmovieapp.domain.repository.Resource
import com.freecast.thatmovieapp.domain.usecase.GetDetailMovieUseCase
import com.freecast.thatmovieapp.domain.usecase.GetMovieVideoUseCase
import org.koin.java.KoinJavaComponent

open class DetailMovieViewModel : BaseViewModel() {
    private val movieRepository: MovieRepository by KoinJavaComponent.inject(MovieRepository::class.java)
    var movieId: Int = 0
    fun fetchMovieDetail(): LiveData<MovieDetail> {
        val result: MutableLiveData<MovieDetail> = MutableLiveData()
        launchCoroutine {
            val useCase = GetDetailMovieUseCase(movieRepository)
            useCase.execute(movieId).collect {
                when (it) {
                    is Resource.Loading -> {
                        _isLoading.postValue(it.isLoading)
                    }

                    is Resource.Success -> {
                        result.postValue(it.data)
                    }

                    is Resource.Error -> {
                        handleError(it.data as BaseException)
                    }
                }
            }
        }
        return result
    }

    fun fetchMovieVideo(): LiveData<MovieVideo> {
        val result: MutableLiveData<MovieVideo> = MutableLiveData()
        launchCoroutine {
            val useCase = GetMovieVideoUseCase(movieRepository)
            useCase.execute(movieId).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { videos ->
                            getVideo(videos)?.let { video ->
                                result.postValue(video)
                            }
                        }

                    }

                    is Resource.Error -> {
                        handleError(it.data as BaseException)
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
        return result
    }

    private fun getVideo(videos: List<MovieVideo>, site: String = "YouTube", type: String = "Trailer"): MovieVideo? {
        videos.forEach {
            if (it.site == site && it.type == type) {
                return it
            }
        }
        return null
    }
}