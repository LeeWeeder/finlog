package com.leeweeder.finlog.data.di

import com.leeweeder.finlog.data.remote.CurrenciesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object  NetworkModule {

    private const val BASE_URL = "https://worker.currencies-finlog.workers.dev/"

    @Provides
    @Singleton
    fun providesRetrofit(json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun providesCurrenciesApi(retrofit: Retrofit): CurrenciesApi =
        retrofit.create(CurrenciesApi::class.java)
}