package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.movielist.MovieListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieListActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeMovieListActivity(): MovieListActivity
}