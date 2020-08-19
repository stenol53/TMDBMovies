package com.voak.android.tmdbmovies.ui.details.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.MovieDetails
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.model.Video
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

    fun onAttached(id: Int) {
        Log.i(TAG, this.toString())
        _loading.value = true
        movieDetailsRepository.getData(id) {
            if (it is Result.Success) {
                val map = it.data as Map<String, Any>
                _movie.value = map[MovieDetailsRepository.MOVIE_DETAILS] as MovieDetails
                _videos.value = map[MovieDetailsRepository.VIDEOS] as List<Video>

                Log.i(TAG, _videos.value.toString())
            } else {
                Log.i(TAG, (it as Result.Error).errorMessage)
            }
            _loading.value = false
        }
    }

}