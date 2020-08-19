package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class CastResult(
    @SerializedName("cast")
    val cast: List<Cast>
)