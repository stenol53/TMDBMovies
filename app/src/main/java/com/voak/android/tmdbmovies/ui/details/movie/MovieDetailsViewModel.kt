package com.voak.android.tmdbmovies.ui.details.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.*
import com.voak.android.tmdbmovies.repository.MovieDetailsRepository
import java.util.*
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) : ViewModel() {

    private val TAG: String = "MovieDetailsViewModel"

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading = _loading
    private val _movie = MutableLiveData<MovieDetails>().apply { value = null }
    val movie = _movie
    private val _videos = MutableLiveData<List<Video>>().apply { value = Collections.emptyList() }
    val videos = _videos
    private val _cast = MutableLiveData<List<Cast>>().apply { value = Collections.emptyList() }
    val cast = _cast
    private val _similarMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val similarMovies = _similarMovies

    private var totalPagesSimilar = 0
    private var currentPageSimilar = 1
    private var currentId: Int = -1
    private var isFetching = false

    fun onAttached(id: Int) {
        _loading.value = true
        currentId = id

        movieDetailsRepository.getData(id) {
            if (it is Result.Success) {
                val map = it.data as Map<String, Any>
                _movie.value = map[MovieDetailsRepository.MOVIE_DETAILS] as MovieDetails
                _videos.value = map[MovieDetailsRepository.VIDEOS] as List<Video>
                _cast.value = map[MovieDetailsRepository.CAST] as List<Cast>
                _similarMovies.value = (map[MovieDetailsRepository.SIMILAR_MOVIES] as MovieResult).result

                totalPagesSimilar = (map[MovieDetailsRepository.SIMILAR_MOVIES] as MovieResult).totalPages
                currentPageSimilar = 1

                Log.i(TAG, _similarMovies.value.toString())
            } else {
                Log.i(TAG, (it as Result.Error).errorMessage.orEmpty())
            }
            _loading.value = false
        }
    }

    fun loadSimilar() {
        if (currentPageSimilar + 1 <= totalPagesSimilar && !isFetching) {
            isFetching = true
            currentPageSimilar++
            movieDetailsRepository.getSimilar(currentId, currentPageSimilar) {
                if (it is Result.Success) {

                    val list = _similarMovies.value?.toMutableList()
                    list?.addAll(it.data as List<Movie>)

                    _similarMovies.value = list?.toList()

                    Log.i(TAG, currentPageSimilar.toString())
                    Log.i(TAG, _similarMovies.value.toString())
                } else {
                    Log.i(TAG, (it as Result.Error).errorMessage.orEmpty())
                }
                isFetching = false
            }
        }
    }

}