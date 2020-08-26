package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class PersonTvShowsResult(
    @SerializedName("cast")
    val tvShows: List<TvShow>
)