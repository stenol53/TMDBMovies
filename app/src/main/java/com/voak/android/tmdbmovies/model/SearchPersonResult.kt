package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class SearchPersonResult(
    @SerializedName("results")
    val result: List<Cast>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)