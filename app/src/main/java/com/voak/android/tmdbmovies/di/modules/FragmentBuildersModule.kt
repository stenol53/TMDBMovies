package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.bottomnavigation.home.HomeFragment
import com.voak.android.tmdbmovies.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}