package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.Result
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.repository.HomeRepository
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val TAG: String = "HomeViewModel"

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val nowPlayingMovies = _nowPlayingMovies
    private val _tvShowsOnTheAir = MutableLiveData<List<TvShow>>().apply { value = Collections.emptyList() }
    val tvShowOnTheAir = _tvShowsOnTheAir
    private val _popularMovies = MutableLiveData<List<Movie>>().apply { value = Collections.emptyList() }
    val popularMovies = _popularMovies
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading = _loading

    fun onAttached() {

        Log.i(TAG, this.toString())

        _loading.value = true
        homeRepository.getAllData {
            if (it is Result.Success) {
                val map = it.data as Map<String, List<Any>>
                _nowPlayingMovies.value = map[HomeRepository.RESULT_1] as List<Movie>
                _tvShowsOnTheAir.value = map[HomeRepository.RESULT_2] as List<TvShow>
                _popularMovies.value = map[HomeRepository.RESULT_3] as List<Movie>
            } else {
                Log.i(TAG, (it as Result.Error).errorMessage)
            }
        }
        _loading.value = false
    }
}