package com.charlie.remote.di

import com.charlie.remote.source.GalleryRemoteDataSource
import com.charlie.remote.source.GalleryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindGalleryService(
        service: GalleryRemoteDataSourceImpl,
    ): GalleryRemoteDataSource
}
