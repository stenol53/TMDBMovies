package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.MovieResult
import com.voak.android.tmdbmovies.model.TvResult
import io.reactivex.Observable
import javax.inject.Inject

class SearchService @Inject constructor(private val apiService: TMDBApiService) {

    fun searchMovies(query: String, page: Int = 1): Observable<MovieResult> {
        return apiService.searchMovies(query, page)
    }

    fun searchTv(query: String, page: Int = 1): Observable<TvResult> {
        return apiService.searchTv(query, page)
    }
}