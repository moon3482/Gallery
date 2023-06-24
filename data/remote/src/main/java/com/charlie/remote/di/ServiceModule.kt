package com.charlie.remote.di

import com.charlie.remote.source.ImageService
import com.charlie.remote.source.ImageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindImageService(
        service: ImageServiceImpl,
    ): ImageService
}
