package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.bottomnavigation.favourite.FavouriteFragment
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.HomeFragment
import com.voak.android.tmdbmovies.ui.bottomnavigation.search.SearchFragment
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import com.voak.android.tmdbmovies.ui.details.tv.TvShowDetailsFragment
import com.voak.android.tmdbmovies.ui.login.LoginFragment
import com.voak.android.tmdbmovies.ui.person.PersonDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeFavouriteFragment(): FavouriteFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeTvShowDetailsFragment(): TvShowDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): PersonDetailsFragment
}