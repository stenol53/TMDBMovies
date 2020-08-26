package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("profile_path")
    val poster: String
)