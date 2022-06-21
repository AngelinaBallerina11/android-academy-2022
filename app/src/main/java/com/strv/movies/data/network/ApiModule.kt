package com.strv.movies.data.network

import com.strv.movies.data.network.auth.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideProfileApi(@AuthorizedRetrofit retrofit: Retrofit): ProfileApi = retrofit.create()

    @Provides
    @Singleton
    fun provideMovieApi(@UnauthorizedRetrofit retrofit: Retrofit): MovieApi = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthApi(@UnauthorizedRetrofit retrofit: Retrofit): AuthApi = retrofit.create()
}