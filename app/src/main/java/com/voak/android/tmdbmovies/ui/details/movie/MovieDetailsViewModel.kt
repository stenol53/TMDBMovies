package com.voak.android.tmdbmovies.ui.details.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Cast
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.MovieDetails
import com.voak.android.tmdbmovies.model.Video
import com.voak.android.tmdbmovies.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) : ViewModel() {

    fun onAttached(id: Int) {
        movieDetailsRepository.getData(id)
    }

    fun loadSimilar(id: Int) {
        movieDetailsRepository.getSimilar(id)
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return movieDetailsRepository.loading
    }

    fun getMovieLiveData(): LiveData<MovieDetails> {
        return movieDetailsRepository.movie
    }

    fun getCastLiveData(): LiveData<List<Cast>> {
        return movieDetailsRepository.cast
    }

    fun getVideosLiveData(): LiveData<List<Video>> {
        return movieDetailsRepository.videos
    }

    fun getSimilarMoviesLiveData(): LiveData<List<Movie>> {
        return movieDetailsRepository.similarMovies
    }

}