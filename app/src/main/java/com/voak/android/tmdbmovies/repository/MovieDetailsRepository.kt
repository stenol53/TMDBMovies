package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.model.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val apiService: TMDBApiService) {

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