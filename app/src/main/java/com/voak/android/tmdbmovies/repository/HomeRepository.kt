package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voak.android.tmdbmovies.api.MovieService
import com.voak.android.tmdbmovies.api.TvShowService
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.MovieResult
import com.voak.android.tmdbmovies.model.TvResult
import com.voak.android.tmdbmovies.model.TvShow
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val movieService: MovieService,
    private val tvShowService: TvShowService
) {

    private val TAG: String = "HomeRepository"

    companion object {
        private const val MOVIES_NOW_PLAYING: String = "movies_now_playing"
        private const val TV_SHOWS_ON_THE_AIR: String = "tv_shows_on_the_air"
        private const val POPULAR_MOVIES: String = "popular_movies"
    }

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val nowPlayingMovies: LiveData<List<Movie>> = _nowPlayingMovies
    private val _tvShowsOnTheAir = MutableLiveData<List<TvShow>>().apply { value = Collections.emptyList() }
    val tvShowOnTheAir: LiveData<List<TvShow>> = _tvShowsOnTheAir
    private val _popularMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val popularMovies: LiveData<List<Movie>> = _popularMovies
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading = _loading

    private var totalPages = 1
    private var currentPage = 0

    @SuppressLint("CheckResult")
    fun getAllData() {
        _loading.value = false
        totalPages = 1
        currentPage = 0
        Observable.zip(
            movieService.getMoviesNowPlaying(),
            tvShowService.getTvShowsOnTheAir(),
            movieService.getPopularMovies(),
            Function3<MovieResult, TvResult, MovieResult, Map<String, List<Any>>> {
                res1, res2, res3 -> mapOf(
                    MOVIES_NOW_PLAYING to res1.result,
                    TV_SHOWS_ON_THE_AIR to res2.result,
                    POPULAR_MOVIES to res3.result
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    _nowPlayingMovies.value = (result[MOVIES_NOW_PLAYING] as? List<Movie>)
                    _tvShowsOnTheAir.value = (result[TV_SHOWS_ON_THE_AIR] as? List<TvShow>)
                    _popularMovies.value = (result[POPULAR_MOVIES] as? List<Movie>)
                    _loading.value = false
                },
                { error ->
                    Log.i(TAG, error.localizedMessage)
                    _loading.value = false
                }
            )
    }

    @SuppressLint("CheckResult")
    fun getPopularMovies() {
        if (currentPage + 1 <= totalPages) {
            currentPage++
            _loading.value = true
            movieService.getPopularMovies(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        totalPages = result.totalPages
                        val list = _popularMovies.value?.toMutableList()
                        list?.addAll(result.result)
                        _popularMovies.value = list?.toList()
                        _loading.value = false
                    },
                    { error ->
                        Log.i(TAG, error.localizedMessage)
                        _loading.value = false
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun getMoviesNowPlaying() {
        if (currentPage + 1 <= totalPages) {
            currentPage++
            _loading.value = true
            movieService.getMoviesNowPlaying(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        totalPages = result.totalPages
                        val list = _nowPlayingMovies.value?.toMutableList()
                        list?.addAll(result.result)
                        _nowPlayingMovies.value = list?.toList()
                        _loading.value = false
                    },
                    { error ->
                        Log.i(TAG, error.localizedMessage)
                        _loading.value = false
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun getTvShowsOnTheAir() {
        if (currentPage + 1 <= totalPages) {
            currentPage++
            tvShowService.getTvShowsOnTheAir(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        totalPages = result.pages
                        val list = _tvShowsOnTheAir.value?.toMutableList()
                        list?.addAll(result.result)
                        _tvShowsOnTheAir.value = list?.toList()
                        _loading.value = false
                    },
                    { error ->
                        Log.i(TAG, error.localizedMessage)
                        _loading.value = false
                    }
                )
        }
    }
}