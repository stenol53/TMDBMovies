package com.voak.android.tmdbmovies.navigation

import androidx.fragment.app.Fragment
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import com.voak.android.tmdbmovies.ui.login.LoginFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewRouter @Inject constructor(){
    var currentActivity: BaseActivity? = null

    fun showAuthorizationFragment() {
        replaceFragment(LoginFragment(), false)
    }

    fun showDetailsFragment(id: Int) {
        replaceFragment(MovieDetailsFragment.instance(id), false)
    }

    private fun replaceFragment(fragment: Fragment, needBackStack: Boolean) {
        currentActivity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragment)
            if (needBackStack) addToBackStack(null)
            commit()
        }
    }

}