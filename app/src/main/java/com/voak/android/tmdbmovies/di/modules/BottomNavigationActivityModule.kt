package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.bottomnavigation.BottomNavigationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BottomNavigationActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeBottomNavigationActivity(): BottomNavigationActivity
}