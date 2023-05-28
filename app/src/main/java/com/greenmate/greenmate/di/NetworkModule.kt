package com.greenmate.greenmate.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.greenmate.greenmate.model.network.FakeGreenMateService
import com.greenmate.greenmate.model.network.GreenMateService
import com.greenmate.greenmate.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideGreenMateService(retrofit: Retrofit): GreenMateService =
        retrofit.create(GreenMateService::class.java)

    @Singleton
    @Provides
    fun provideFakeGreenMateService() = FakeGreenMateService


}