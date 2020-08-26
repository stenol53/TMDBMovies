package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class TvShowDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val widePoster: String,
    @SerializedName("episode_run_time")
    val runtime: List<Int>,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("number_of_episodes")
    val episodesCount: Int,
    @SerializedName("number_of_seasons")
    val seasonsCount: Int,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("first_air_date")
    val release: String
)