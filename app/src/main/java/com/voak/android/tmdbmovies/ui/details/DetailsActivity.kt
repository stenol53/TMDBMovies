package com.voak.android.tmdbmovies.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import dagger.android.support.DaggerAppCompatActivity

class DetailsActivity : DaggerAppCompatActivity() {
    companion object {
        private const val EXTRA_FRAGMENT: String = "fragment"
        private const val EXTRA_ID: String = "id"

        const val MOVIE_FRAGMENT = 1
        const val TV_SHOW_FRAGMENT = 2

        fun instance(fragment: Int, id: Int, context: Context): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_FRAGMENT, fragment)
            intent.putExtra(EXTRA_ID, id)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (savedInstanceState == null) {
            Log.i("DetailsActivity", "CREATED")
            when (intent.getIntExtra(EXTRA_FRAGMENT, -1)) {
                1 -> { openFragment(MovieDetailsFragment.instance(intent.getIntExtra(EXTRA_ID, -1))) }
                2 -> {  }
                else -> { /* Error */}
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.details_container, fragment)
            .commit()
    }
}