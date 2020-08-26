package com.voak.android.tmdbmovies.navigation

import androidx.fragment.app.Fragment
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import com.voak.android.tmdbmovies.ui.details.tv.TvShowDetailsFragment
import com.voak.android.tmdbmovies.ui.login.LoginFragment
import com.voak.android.tmdbmovies.ui.person.PersonDetailsFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewRouter @Inject constructor(){
    var currentActivity: BaseActivity? = null

    fun showAuthorizationFragment() {
        replaceFragment(LoginFragment(), false)
    }

    fun showMovieDetailsFragment(id: Int) {
        replaceFragment(MovieDetailsFragment.instance(id), false)
    }

    fun showTvShowDetailsFragment(id: Int) {
        replaceFragment(TvShowDetailsFragment.instance(id), false)
    }

    fun showPersonDetailsFragment(id: Int) {
        replaceFragment(PersonDetailsFragment.instance(id), false)
    }

    private fun replaceFragment(fragment: Fragment, needBackStack: Boolean) {
        currentActivity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragment)
            if (needBackStack) addToBackStack(null)
            commit()
        }
    }

}