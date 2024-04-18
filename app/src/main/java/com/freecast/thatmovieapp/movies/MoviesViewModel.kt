package com.freecast.thatmovieapp.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.freecast.thatmovieapp.core.ui.BaseViewModel
import com.freecast.thatmovieapp.data.remote.MovieClient
import com.freecast.thatmovieapp.data.remote.exceptions.BaseException
import com.freecast.thatmovieapp.data.repository.MovieRepositoryImpl
import com.freecast.thatmovieapp.domain.model.Movie
import com.freecast.thatmovieapp.domain.repository.Resource
import com.freecast.thatmovieapp.domain.usecase.GetMoviesUseCase

open class MoviesViewModel(private var endpoint: String, val title: String) : BaseViewModel() {
    private val useCase: GetMoviesUseCase = GetMoviesUseCase(MovieRepositoryImpl(MovieClient.apiService))

    fun fetchMovies(): LiveData<List<Movie>> {
        val result: MutableLiveData<List<Movie>> = MutableLiveData()
        launchCoroutine {
            useCase.execute(endpoint).collect {
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

    fun fetchMoviesByGenreId(genreId: Int): LiveData<List<Movie>> {
        val result: MutableLiveData<List<Movie>> = MutableLiveData()
        launchCoroutine {
            useCase.execute(genreId).collect {
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

    fun setEndpoint(endpoint: String) {
        this.endpoint = endpoint
    }

    private fun getPageTransformer(): CompositePageTransformer {
        return CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer(ViewPager2.PageTransformer() { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }
    }
}