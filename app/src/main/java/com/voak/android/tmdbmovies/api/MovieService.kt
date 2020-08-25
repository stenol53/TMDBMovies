package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.CastResult
import com.voak.android.tmdbmovies.model.MovieDetails
import com.voak.android.tmdbmovies.model.MovieResult
import com.voak.android.tmdbmovies.model.VideoResult
import io.reactivex.Observable
import javax.inject.Inject

class MovieService @Inject constructor(private val apiService: TMDBApiService) {

    fun getMoviesNowPlaying(page: Int = 1): Observable<MovieResult> {
        return apiService.getMoviesNowPlaying(page)
    }

    fun getPopularMovies(page: Int = 1): Observable<MovieResult> {
        return apiService.getPopularMovies(page)
    }

    fun getMovieDetails(id: Int): Observable<MovieDetails> {
        return apiService.getMovieDetails(id)
    }

    fun getVideos(id: Int): Observable<VideoResult> {
        return apiService.getVideos(id)
    }

    fun getCast(id: Int): Observable<CastResult> {
        return apiService.getCast(id)
    }

    fun getSimilarMovies(id: Int, page: Int = 1): Observable<MovieResult> {
        return apiService.getSimilarMovies(id, page)
    }
}