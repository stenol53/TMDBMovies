package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.RequestTokenResponse
import com.voak.android.tmdbmovies.model.SessionResponse
import io.reactivex.Observable
import javax.inject.Inject

class ClientService @Inject constructor(private val apiService: TMDBApiService) {

    fun createRequestToken(): Observable<RequestTokenResponse> {
        return apiService.createRequestToken()
    }

    fun validateRequestToken(username: String, password: String, token: String): Observable<RequestTokenResponse> {
        return apiService.validateRequestToken(username, password, token)
    }

    fun createSession(token: String): Observable<SessionResponse> {
        return apiService.createSession(token)
    }
}