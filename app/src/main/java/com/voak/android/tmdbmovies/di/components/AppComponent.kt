package com.voak.android.tmdbmovies.di.components

import android.app.Application
import com.voak.android.tmdbmovies.BaseApp
import com.voak.android.tmdbmovies.di.modules.AppModule
import com.voak.android.tmdbmovies.di.modules.BottomNavigationActivityModule
import com.voak.android.tmdbmovies.di.modules.DetailsActivityModule
import com.voak.android.tmdbmovies.di.modules.LoginActivityModule
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
        LoginActivityModule::class,
        BottomNavigationActivityModule::class,
        DetailsActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApp>{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(baseApp: BaseApp)
}