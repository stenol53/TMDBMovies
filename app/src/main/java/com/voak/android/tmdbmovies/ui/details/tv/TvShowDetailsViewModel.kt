package com.voak.android.tmdbmovies.ui.details.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Cast
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.model.TvShowDetails
import com.voak.android.tmdbmovies.model.Video
import com.voak.android.tmdbmovies.repository.TvShowDetailsRepository
import javax.inject.Inject

class TvShowDetailsViewModel @Inject constructor(private val tvShowDetailsRepository: TvShowDetailsRepository): ViewModel() {

    fun onAttached(id: Int) {
        tvShowDetailsRepository.getData(id)
    }

    fun loadSimilar(id: Int) {
        tvShowDetailsRepository.getSimilar(id)
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return tvShowDetailsRepository.loading
    }

    fun getTvShowLiveData(): LiveData<TvShowDetails> {
        return tvShowDetailsRepository.tvShow
    }

    fun getVideosLiveData(): LiveData<List<Video>> {
        return tvShowDetailsRepository.videos
    }

    fun getCastLiveData(): LiveData<List<Cast>> {
        return tvShowDetailsRepository.cast
    }

    fun getSimilarTvShowsLiveData(): LiveData<List<TvShow>> {
        return tvShowDetailsRepository.similarTvShows
    }
}