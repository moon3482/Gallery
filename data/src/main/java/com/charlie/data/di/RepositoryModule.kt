package com.charlie.data.di

import com.charlie.data.repository.ImageRepository
import com.charlie.data.repository.ImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl,
    ): ImageRepository
}