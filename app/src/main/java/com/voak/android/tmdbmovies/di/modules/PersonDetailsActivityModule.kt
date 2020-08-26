package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.ui.person.PersonDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PersonDetailsActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributePersonDetailsActivity(): PersonDetailsActivity

}