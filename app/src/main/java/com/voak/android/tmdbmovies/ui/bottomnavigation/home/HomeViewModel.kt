package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.repository.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val TAG: String = "HomeViewModel"

    fun onAttached() {
        homeRepository.getAllData()
    }

    fun getNowPlayingMoviesLiveData(): LiveData<List<Movie>> {
        return homeRepository.nowPlayingMovies
    }

    fun getTvShowsOnTheAirLiveData(): LiveData<List<TvShow>> {
        return homeRepository.tvShowOnTheAir
    }

    fun getPopularMoviesLiveData(): LiveData<List<Movie>> {
        return homeRepository.popularMovies
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return  homeRepository.loading
    }
}