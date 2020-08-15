package com.voak.android.tmdbmovies.ui.details.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.MovieDetails
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) : ViewModel() {

    private val TAG: String = "MovieDetailsViewModel"

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading = _loading
    private val _movie = MutableLiveData<MovieDetails>().apply { value = null }
    val movie = _movie

    fun onAttached(id: Int) {
        Log.i(TAG, this.toString())
        _loading.value = true
        movieDetailsRepository.getMovieDetails(id) {
            if (it is Result.Success) {
                Log.i(TAG, "okokokokokok")
                _movie.value = it.data as MovieDetails
            } else {
                Log.i(TAG, (it as Result.Error).errorMessage)
            }
            _loading.value = false
        }
    }

}