package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.*
import com.voak.android.tmdbmovies.utils.REGION
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

    @GET("movie/now_playing")
    fun getMoviesNowPlaying(
        @Query("page") page: Int = 1,
        @Query("region") region: String = REGION
    ): Observable<MovieResult>

    @GET("tv/on_the_air")
    fun getTvShowsOnTheAir(@Query("page") page: Int = 1): Observable<TvResult>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("region") region: String = REGION
    ): Observable<MovieResult>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int): Observable<MovieDetails>

    @GET("movie/{id}/videos")
    fun getVideos(@Path("id") id: Int): Observable<VideoResult>
}