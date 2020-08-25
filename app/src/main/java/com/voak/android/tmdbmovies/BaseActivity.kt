package com.voak.android.tmdbmovies

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.voak.android.tmdbmovies.navigation.ViewRouter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewRouter: ViewRouter

    @LayoutRes
    protected open fun getLayoutRes() : Int {
        return -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        viewRouter.currentActivity = this
    }

    override fun onDestroy() {
        super.onDestroy()
        viewRouter.currentActivity = null
    }
}