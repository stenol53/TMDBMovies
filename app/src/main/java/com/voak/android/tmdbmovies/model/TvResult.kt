package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class TvResult(
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("results")
    val result: List<TvShow>
)