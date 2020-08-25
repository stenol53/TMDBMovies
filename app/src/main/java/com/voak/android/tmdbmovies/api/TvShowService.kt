package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.TvResult
import io.reactivex.Observable
import javax.inject.Inject

class TvShowService @Inject constructor(private val apiService: TMDBApiService) {

    fun getTvShowsOnTheAir(page: Int = 1): Observable<TvResult> {
        return apiService.getTvShowsOnTheAir(page)
    }
}