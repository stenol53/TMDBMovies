package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.voak.android.tmdbmovies.api.TvShowService
import com.voak.android.tmdbmovies.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class TvShowDetailsRepository @Inject constructor(private val tvShowService: TvShowService) {

    companion object {
        private const val TAG = "TvShowDetailsRepository"
        private const val TV_SHOW_DETAILS: String = "tv_show_details"
        private const val VIDEOS: String = "videos"
        private const val CAST: String = "cast"
        private const val SIMILAR_TV_SHOWS = "similar_tv_shows"
    }

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading
    private val _tvShow = MutableLiveData<TvShowDetails>().apply { value = null }
    val tvShow: LiveData<TvShowDetails> = _tvShow
    private val _videos = MutableLiveData<List<Video>>().apply { value = Collections.emptyList() }
    val videos: LiveData<List<Video>> = _videos
    private val _cast = MutableLiveData<List<Cast>>().apply { value = Collections.emptyList() }
    val cast: LiveData<List<Cast>> = _cast
    private val _similarTvShows = MutableLiveData<List<TvShow>>().apply { value = Collections.emptyList() }
    val similarTvShows: LiveData<List<TvShow>> = _similarTvShows

    private var totalPagesSimilar = 0
    private var currentPageSimilar = 1
    private var currentId: Int = -1
    private var isFetching = false

    @SuppressLint("CheckResult")
    fun getData(id: Int) {
        _loading.value = true
        Observable.zip(
            tvShowService.getTvShowDetails(id),
            tvShowService.getTvShowVideos(id),
            tvShowService.getCast(id),
            tvShowService.getSimilarTvShows(id),
            Function4<TvShowDetails, VideoResult, CastResult, TvResult, Map<String, Any>> {
                    details, videos, cast, similar ->
                mapOf(
                    TV_SHOW_DETAILS to details,
                    VIDEOS to videos.result,
                    CAST to cast.cast,
                    SIMILAR_TV_SHOWS to similar
                )
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    _tvShow.value = result[TV_SHOW_DETAILS] as TvShowDetails
                    _videos.value = result[VIDEOS] as List<Video>
                    _cast.value = result[CAST] as List<Cast>
                    _similarTvShows.value = (result[SIMILAR_TV_SHOWS] as TvResult).result

                    totalPagesSimilar = (result[SIMILAR_TV_SHOWS] as MovieResult).totalPages
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
            tvShowService.getSimilarTvShows(id, currentPageSimilar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val list = _similarTvShows.value?.toMutableList()
                        list?.addAll(result.result)

                        _similarTvShows.value = list?.toList()

                        Log.i(TAG, currentPageSimilar.toString())
                        Log.i(TAG, _similarTvShows.value.toString())
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