package com.voak.android.tmdbmovies.api

import com.voak.android.tmdbmovies.model.PersonDetails
import com.voak.android.tmdbmovies.model.PersonMoviesResult
import com.voak.android.tmdbmovies.model.PersonTvShowsResult
import io.reactivex.Observable
import javax.inject.Inject

class PersonDetailsService @Inject constructor(private val apiService: TMDBApiService) {

    fun getPersonDetails(id: Int): Observable<PersonDetails> {
        return apiService.getPersonDetails(id)
    }

    fun getPersonMovies(id: Int): Observable<PersonMoviesResult> {
        return apiService.getPersonMovies(id)
    }

    fun getPersonTvShows(id: Int): Observable<PersonTvShowsResult> {
        return apiService.getPersonTv(id)
    }
}