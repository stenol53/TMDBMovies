package com.voak.android.tmdbmovies.model

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("request_token")
    val requestToken: String
)