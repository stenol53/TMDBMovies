package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voak.android.tmdbmovies.api.MovieService
import com.voak.android.tmdbmovies.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val movieService: MovieService) {

    companion object {
        private const val TAG = "MovieDetailsRepository"
        private const val MOVIE_DETAILS: String = "movie_details"
        private const val VIDEOS: String = "videos"
        private const val CAST: String = "cast"
        private const val SIMILAR_MOVIES = "similar_movies"
    }

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val _movie = MutableLiveData<MovieDetails>().apply { value = null }
    val movie: LiveData<MovieDetails> = _movie
    private val _videos = MutableLiveData<List<Video>>().apply { value = Collections.emptyList() }
    val videos: LiveData<List<Video>> = _videos
    private val _cast = MutableLiveData<List<Cast>>().apply { value = Collections.emptyList() }
    val cast: LiveData<List<Cast>> = _cast
    private val _similarMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val similarMovies: LiveData<List<Movie>> = _similarMovies

    private var totalPagesSimilar = 0
    private var currentPageSimilar = 1
    private var currentId: Int = -1
    private var isFetching = false

    @SuppressLint("CheckResult")
    fun getData(id: Int) {
        _loading.value = true
        Observable.zip(
            movieService.getMovieDetails(id),
            movieService.getVideos(id),
            movieService.getCast(id),
            movieService.getSimilarMovies(id),
            Function4<MovieDetails, VideoResult, CastResult, MovieResult, Map<String, Any>> {
                    details, videos, cast, similar ->
                mapOf(
                    MOVIE_DETAILS to details,
                    VIDEOS to videos.result,
                    CAST to cast.cast,
                    SIMILAR_MOVIES to similar
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    _movie.value = result[MOVIE_DETAILS] as MovieDetails
                    _videos.value = result[VIDEOS] as List<Video>
                    _cast.value = result[CAST] as List<Cast>
                    _similarMovies.value = (result[SIMILAR_MOVIES] as MovieResult).result

                    totalPagesSimilar = (result[SIMILAR_MOVIES] as MovieResult).totalPages
                    currentPageSimilar = 1

                    _loading.value = false
                },
                { error ->
                    Log.i(TAG, error.localizedMessage.orEmpty())
                    _loading.value = false
                }
            )
    }

    @SuppressLint("CheckResult")
    fun getSimilar(id: Int) {
        if (currentPageSimilar + 1 <= totalPagesSimilar && !isFetching) {
            isFetching = true
            currentPageSimilar++
            movieService.getSimilarMovies(id, currentPageSimilar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val list = _similarMovies.value?.toMutableList()
                        list?.addAll(result.result)

                        _similarMovies.value = list?.toList()

                        Log.i(TAG, currentPageSimilar.toString())
                        Log.i(TAG, _similarMovies.value.toString())
                        isFetching = false
                    },
                    { error ->
                        Log.i(TAG, error.localizedMessage.orEmpty())
                        isFetching = false
                    }
                )
        }
    }
}