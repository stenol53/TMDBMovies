package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("results")
    val result: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)