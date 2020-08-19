package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String
)