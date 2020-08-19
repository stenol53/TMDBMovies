package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val result: List<Video>
)