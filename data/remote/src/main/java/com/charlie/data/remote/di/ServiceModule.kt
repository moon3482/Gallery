package com.charlie.data.remote.di

import com.charlie.data.remote.source.ImageService
import com.charlie.data.remote.source.ImageServiceImpl
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
