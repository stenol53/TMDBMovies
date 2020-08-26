package com.voak.android.tmdbmovies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voak.android.tmdbmovies.di.annotations.ViewModelKey
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.HomeViewModel
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsViewModel
import com.voak.android.tmdbmovies.ui.details.tv.TvShowDetailsViewModel
import com.voak.android.tmdbmovies.ui.login.LoginFragmentViewModel
import com.voak.android.tmdbmovies.ui.person.PersonDetailsViewModel
import com.voak.android.tmdbmovies.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    internal abstract fun bindLoginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    internal abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailsViewModel::class)
    internal abstract fun bindTvShowDetailsViewModel(viewModel: TvShowDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailsViewModel::class)
    internal abstract fun bindPersonDetailsViewModel(viewModel: PersonDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}