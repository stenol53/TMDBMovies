package com.voak.android.tmdbmovies.di.modules

import com.voak.android.tmdbmovies.api.TMDBApiService
import com.voak.android.tmdbmovies.utils.API_KEY
import com.voak.android.tmdbmovies.utils.BASE_URL
import com.voak.android.tmdbmovies.utils.LANGUAGE
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    private val interceptor =Interceptor {chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", LANGUAGE)
            .build()

        val newRequest =chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideTMDBApiService(retrofit: Retrofit): TMDBApiService {
        return retrofit.create(TMDBApiService::class.java)
    }

}