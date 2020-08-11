package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.model.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginRepository @Inject constructor(private val apiService: TMDBApiService) {

    private val TAG: String = "LoginRepository"

    @SuppressLint("CheckResult")
    fun makeAuth(username: String, password: String, callback: (Result<Any>) -> Unit) {
        apiService.createRequestToken()
            .switchMap { t -> apiService.validateRequestToken(username, password, t.requestToken) }
            .switchMap { t -> apiService.createSession(t.requestToken) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> callback(Result.Success(result.sessionId)) },
                { error -> callback(Result.Error(error.localizedMessage)) }
            )
    }
}
