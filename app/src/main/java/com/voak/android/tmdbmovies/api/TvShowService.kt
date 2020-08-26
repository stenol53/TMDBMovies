package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.CastResult
import com.voak.android.tmdbmovies.model.TvResult
import com.voak.android.tmdbmovies.model.TvShowDetails
import com.voak.android.tmdbmovies.model.VideoResult
import io.reactivex.Observable
import javax.inject.Inject

class TvShowService @Inject constructor(private val apiService: TMDBApiService) {

    fun getTvShowsOnTheAir(page: Int = 1): Observable<TvResult> {
        return apiService.getTvShowsOnTheAir(page)
    }

    fun getTvShowDetails(id: Int): Observable<TvShowDetails> {
        return apiService.getTvShowDetails(id)
    }

    fun getTvShowVideos(id: Int): Observable<VideoResult> {
        return apiService.getTvShowVideos(id)
    }

    fun getSimilarTvShows(id: Int, page: Int = 1): Observable<TvResult> {
        return apiService.getSimilarTvShows(id, page)
    }

    fun getCast(id: Int): Observable<CastResult> {
        return apiService.getTvShowCast(id)
    }
}