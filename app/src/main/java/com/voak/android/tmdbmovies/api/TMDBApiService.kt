package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.RequestTokenResponse
import com.voak.android.tmdbmovies.model.SessionResponse
import io.reactivex.Observable
import retrofit2.http.*

interface TMDBApiService {

    @GET("authentication/token/new")
    fun createRequestToken(): Observable<RequestTokenResponse>

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    fun validateRequestToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") token: String
    ): Observable<RequestTokenResponse>

    @FormUrlEncoded
    @POST("authentication/session/new")
    fun createSession(@Field("request_token") token: String): Observable<SessionResponse>

}