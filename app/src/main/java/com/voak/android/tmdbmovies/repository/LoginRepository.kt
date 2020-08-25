package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voak.android.tmdbmovies.api.ClientService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginRepository @Inject constructor(private val clientService: ClientService) {

    private val TAG: String = "LoginRepository"

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val _loginError = MutableLiveData<String?>().apply { value = null }
    val loginError: LiveData<String?> = _loginError
    private val _sessionId = MutableLiveData<String?>().apply { value = null }
    val sessionId: LiveData<String?> = _sessionId


    @SuppressLint("CheckResult")
    fun makeAuth(username: String, password: String) {
        _loading.value = true
        clientService.createRequestToken()
            .switchMap { t -> clientService.validateRequestToken(username, password, t.requestToken) }
            .switchMap { t -> clientService.createSession(t.requestToken) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> _sessionId.value = result.sessionId },
                { error -> _loginError.value = error.localizedMessage }
            )
    }

}
