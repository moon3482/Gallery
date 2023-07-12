package com.charlie.data.local.di

import android.content.Context
import androidx.room.Room
import com.charlie.data.local.db.GalleryDB
import com.charlie.data.local.db.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideGalleryDB(
        @ApplicationContext context: Context,
    ): GalleryDB {
        return Room
            .databaseBuilder(
                context,
                GalleryDB::class.java,
                "gallery.db"
            ).build()
    }

    @Provides
    @Singleton
    fun provideImageDao(
        galleryDB: GalleryDB,
    ): ImageDao {
        return galleryDB.imageDao()
    }
}