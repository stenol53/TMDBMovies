package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeLoginActivity(): LoginActivity
}