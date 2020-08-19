package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val apiService: TMDBApiService) {

    companion object {
        const val MOVIE_DETAILS: String = "movie_details"
        const val VIDEOS: String = "videos"
        const val CAST: String = "cast"
        const val SIMILAR_MOVIES = "similar_movies"
    }

    @SuppressLint("CheckResult")
    fun getData(id: Int, callback: (Result<Any>) -> Unit) {
        Observable.zip(
            apiService.getMovieDetails(id),
            apiService.getVideos(id),
            apiService.getCast(id),
            apiService.getSimilarMovies(id),
            Function4<MovieDetails, VideoResult, CastResult, MovieResult, Map<String, Any>> {
                    details, videos, cast, similar ->
                mapOf(
                    MOVIE_DETAILS to details,
                    VIDEOS to videos.result,
                    CAST to cast.cast,
                    SIMILAR_MOVIES to similar
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    callback(Result.Success(result))
                },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )
    }

    @SuppressLint("CheckResult")
    fun getSimilar(id: Int, page: Int, callback: (Result<Any>) -> Unit) {
        apiService.getSimilarMovies(id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> callback(Result.Success(result.result)) },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )
    }
}