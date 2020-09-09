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
    fun getMovieVideos(@Path("id") id: Int): Observable<VideoResult>

    @GET("movie/{id}/credits")
    fun getCast(@Path("id") id: Int): Observable<CastResult>

    @GET("movie/{id}/similar")
    fun getSimilarMovies(
        @Path("id") id: Int,
        @Query("page") page: Int = 1
    ): Observable<MovieResult>

    @GET("tv/{id}")
    fun getTvShowDetails(@Path("id") id: Int): Observable<TvShowDetails>

    @GET("tv/{id}/videos")
    fun getTvShowVideos(@Path("id") id: Int): Observable<VideoResult>

    @GET("tv/{id}/similar")
    fun getSimilarTvShows(
        @Path("id") id: Int,
        @Query("page") page: Int = 1
    ): Observable<TvResult>

    @GET("tv/{id}/credits")
    fun getTvShowCast(@Path("id") id: Int): Observable<CastResult>

    @GET("person/{id}")
    fun getPersonDetails(@Path("id") id: Int): Observable<PersonDetails>

    @GET("person/{id}/movie_credits")
    fun getPersonMovies(@Path("id") id: Int): Observable<PersonMoviesResult>

    @GET("person/{id}/tv_credits")
    fun getPersonTv(@Path("id") id: Int): Observable<PersonTvShowsResult>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("region") region: String = REGION
    ): Observable<MovieResult>

    @GET("search/tv")
    fun searchTv(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Observable<TvResult>

//    @GET("search/people")
//    fun searchPeople(
//        @Query("query") query: String,
//        @Query("page") page: Int = 1,
//        @Query("region") region: String = REGION
//    ): Observable<>
}