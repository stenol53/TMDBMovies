package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.model.MovieDetails
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.model.VideoResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val apiService: TMDBApiService) {

    companion object {
        const val MOVIE_DETAILS: String = "movie_details"
        const val VIDEOS: String = "videos"
    }

    @SuppressLint("CheckResult")
    fun getData(id: Int, callback: (Result<Any>) -> Unit) {
        Observable.zip(
            apiService.getMovieDetails(id),
            apiService.getVideos(id),
            BiFunction<MovieDetails, VideoResult, Map<String, Any>> { movieDetails, videos ->
                mapOf(
                    MOVIE_DETAILS to movieDetails,
                    VIDEOS to videos.result
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("MovieDetailsRepository", "RESULT_OK")
                    callback(Result.Success(result))
                },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )

    }

    @SuppressLint("CheckResult")
    fun getMovieDetails(id: Int, callback: (Result<Any>) -> Unit) {
        apiService.getMovieDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("MovieDetailsRepository", "result OK")
                    callback(Result.Success(result)) },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )
    }
}