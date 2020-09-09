package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class TvResult(
    @SerializedName("total_pages")
    val pages: Int,
    @SerializedName("results")
    val result: List<TvShow>,
    @SerializedName("total_results")
    val totalResults: Int
)