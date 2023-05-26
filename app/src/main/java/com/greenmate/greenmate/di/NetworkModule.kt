package com.greenmate.greenmate.di

import com.greenmate.greenmate.model.network.FakeGreenMateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGreenMateService() = FakeGreenMateService


}