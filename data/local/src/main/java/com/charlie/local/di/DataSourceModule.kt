package com.charlie.local.di

import com.charlie.local.source.GalleryLocalDataSource
import com.charlie.local.source.GalleryLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(
        galleryLocalDataSourceImpl: GalleryLocalDataSourceImpl,
    ): GalleryLocalDataSource
}