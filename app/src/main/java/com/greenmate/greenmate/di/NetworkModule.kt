package com.greenmate.greenmate.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.greenmate.greenmate.model.network.FakeGreenMateService
import com.greenmate.greenmate.model.network.GreenMateImageService
import com.greenmate.greenmate.model.network.GreenMateService
import com.greenmate.greenmate.util.BASE_URL
import com.greenmate.greenmate.util.IMAGE_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    @Named("data")
    fun provideDataRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    @Named("image")
    fun provideImageRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(IMAGE_BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideGreenMateService(@Named("data") retrofit: Retrofit): GreenMateService =
        retrofit.create(GreenMateService::class.java)

    @Singleton
    @Provides
    fun provideGreenMateImageService(@Named("image") retrofit: Retrofit): GreenMateImageService =
        retrofit.create(GreenMateImageService::class.java)

    @Singleton
    @Provides
    fun provideFakeGreenMateService() = FakeGreenMateService


}