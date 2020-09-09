package com.voak.android.tmdbmovies.ui.bottomnavigation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.repository.SearchRepository
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository): ViewModel() {

    fun bindSearchView(searchView: TextInputEditText) {
        searchRepository.searchMovies(searchView)
    }

    fun getMoviesLiveData(): LiveData<List<Movie>> {
        return searchRepository.movies
    }

    fun getTvShowsLiveData(): LiveData<List<TvShow>> {
        return searchRepository.tvShows
    }

    fun getMoviesTotalResultsLiveData(): LiveData<Int> {
        return searchRepository.moviesTotalResults
    }

    fun getTvTotalResultsLiveData(): LiveData<Int> {
        return searchRepository.tvTotalResults
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return searchRepository.progress
    }

    fun updateMovies() {
        searchRepository.updateMovies()
    }

    fun updateTv() {
        searchRepository.updateTv()
    }
}