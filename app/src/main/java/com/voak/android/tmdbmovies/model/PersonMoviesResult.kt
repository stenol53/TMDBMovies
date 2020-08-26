package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class PersonMoviesResult(
    @SerializedName("cast")
    val movies: List<Movie>
)