package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voak.android.tmdbmovies.api.PersonDetailsService
import com.voak.android.tmdbmovies.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonDetailsRepository @Inject constructor(private val personDetailsService: PersonDetailsService) {

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val _personLiveData = MutableLiveData<PersonDetails>()
    val personLiveData: LiveData<PersonDetails> = _personLiveData
    private val _personMovies = MutableLiveData<List<Movie>>()
    val personMovies: LiveData<List<Movie>> = _personMovies
    private val _personTvShows = MutableLiveData<List<TvShow>>()
    val personTvShows: LiveData<List<TvShow>> = _personTvShows


    @SuppressLint("CheckResult")
    fun getAllData(id: Int) {
        _loading.value = true
        Observable.zip(
            personDetailsService.getPersonDetails(id),
            personDetailsService.getPersonMovies(id),
            personDetailsService.getPersonTvShows(id),
            Function3<PersonDetails, PersonMoviesResult, PersonTvShowsResult, Map<String, Any>> {
                details, movies, tvShows ->

                mapOf(
                    DETAILS to details,
                    MOVIES to movies.movies,
                    TV_SHOWS to tvShows.tvShows
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i(TAG, "success")
                    _personLiveData.value = result[DETAILS] as? PersonDetails
                    _personMovies.value = result[MOVIES] as? List<Movie>
                    _personTvShows.value = result[TV_SHOWS] as? List<TvShow>
                    _loading.value = false

                    Log.i(TAG, _personMovies.value.toString())
                    Log.i(TAG, _personTvShows.value.toString())
                },
                { error ->
                    Log.i(TAG, error.localizedMessage.orEmpty())
                    Log.i(TAG, error.message)
                    Log.i(TAG, error.stackTrace.toString())
                    Log.i(TAG, error.localizedMessage)
                    _loading.value = false
                }
            )
    }

    companion object {
        private const val TAG = "PersonDetailsRepository"
        private const val DETAILS = "details"
        private const val MOVIES = "movies"
        private const val TV_SHOWS = "tv_shows"
    }
}