package com.voak.android.tmdbmovies.ui.movielist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R

class MovieListActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_movie_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent.getIntExtra(KEY_DATA, -1)) {
            EXTRA_POPULAR_MOVIES_DATA -> {
                viewRouter.showPopularMoviesListFragment()
            }
            EXTRA_MOVIES_NOW_PLAYING_DATA -> {
                viewRouter.showMoviesNowPlayingListFragment()
            }
            EXTRA_TV_SHOW_DATA -> {
                viewRouter.showTvShowsOnTheAirListFragment()
            }
        }
    }

    companion object {
        private const val KEY_DATA = "key_data"
        const val EXTRA_POPULAR_MOVIES_DATA = 0
        const val EXTRA_TV_SHOW_DATA = 1
        const val EXTRA_MOVIES_NOW_PLAYING_DATA = 2

        fun newIntent(data: Int , context: Context): Intent {
            val intent = Intent(context, MovieListActivity::class.java)
            when (data) {
                EXTRA_MOVIES_NOW_PLAYING_DATA, EXTRA_POPULAR_MOVIES_DATA, EXTRA_TV_SHOW_DATA -> {
                    intent.putExtra(KEY_DATA, data)
                }
            }
            return intent
        }
    }
}