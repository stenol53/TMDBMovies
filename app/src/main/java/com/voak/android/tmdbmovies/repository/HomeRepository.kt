package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.model.MovieResult
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.model.TvResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: TMDBApiService) {

    private val TAG: String = "HomeRepository"

    companion object {
        const val RESULT_1: String = "result_1"
        const val RESULT_2: String = "result_2"
        const val RESULT_3: String = "result_3"
    }

    @SuppressLint("CheckResult")
    fun getAllData(callback: (Result<Any>) -> Unit) {
        Observable.zip(
            apiService.getMoviesNowPlaying(),
            apiService.getTvShowsOnTheAir(),
            apiService.getPopularMovies(),
            Function3<MovieResult, TvResult, MovieResult, Map<String, List<Any>>> {
                res1, res2, res3 -> mapOf(
                    RESULT_1 to res1.result,
                    RESULT_2 to res2.result,
                    RESULT_3 to res3.result
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> callback(Result.Success(result)) },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )
    }
}