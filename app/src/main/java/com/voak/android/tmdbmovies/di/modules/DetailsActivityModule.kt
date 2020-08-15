package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.details.DetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailsActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeDetailsActivity(): DetailsActivity

}