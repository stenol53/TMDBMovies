package com.voak.android.tmdbmovies.di.components

import android.app.Application
import com.voak.android.tmdbmovies.BaseApp
import com.voak.android.tmdbmovies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        LoginActivityModule::class,
        BottomNavigationActivityModule::class,
        DetailsActivityModule::class,
        PersonDetailsActivityModule::class,
        MovieListActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApp>{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApp): Builder

        fun build(): AppComponent
    }

    override fun inject(baseApp: BaseApp)
}