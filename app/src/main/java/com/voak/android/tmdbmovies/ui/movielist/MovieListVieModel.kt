package com.voak.android.tmdbmovies.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.repository.HomeRepository
import javax.inject.Inject

class MovieListVieModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    fun getPopularMovies() {
        homeRepository.getPopularMovies()
    }

    fun getMoviesNowPlaying() {
        homeRepository.getMoviesNowPlaying()
    }

    fun getTvShowsOnTheAir() {
        homeRepository.getTvShowsOnTheAir()
    }

    fun getPopularMoviesLiveData(): LiveData<List<Movie>> {
        return homeRepository.popularMovies
    }

    fun getMoviesNowPlayingLiveData(): LiveData<List<Movie>> {
        return homeRepository.nowPlayingMovies
    }

    fun getTvShowsOnTheAirLiveData(): LiveData<List<TvShow>> {
        return homeRepository.tvShowOnTheAir
    }

}