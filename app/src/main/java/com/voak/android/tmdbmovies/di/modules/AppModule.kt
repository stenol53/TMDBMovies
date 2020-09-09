package com.voak.android.tmdbmovies.di.modules

import android.content.Context
import com.voak.android.tmdbmovies.BaseApp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: BaseApp): Context {
        return app.applicationContext
    }
}