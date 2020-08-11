package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.repository.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val TAG: String = "HomeViewModel"

    fun test(view: View) {
        Log.i(TAG, "test")
    }

}