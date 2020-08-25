package com.voak.android.tmdbmovies.ui.login

import android.os.Bundle
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R

class LoginActivity : BaseActivity() {

    override fun getLayoutRes() : Int {
        return R.layout.activity_auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewRouter.showAuthorizationFragment()
    }

}