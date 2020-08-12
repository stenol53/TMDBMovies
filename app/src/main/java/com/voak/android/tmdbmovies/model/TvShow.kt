package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val widePosterPath: String,
    @SerializedName("name")
    val title: String
)