package com.voak.android.tmdbmovies.utils

import android.content.Context
import androidx.preference.PreferenceManager

object SharedPreferences {

    private const val PREF_SEARCH_QUERY = "search_query"

    fun setSearchQuery(context: Context, query: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_SEARCH_QUERY, query)
            .apply()
    }

    fun getSearchQuery(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(PREF_SEARCH_QUERY, null)
    }

}