package com.charlie.gallery.local

import android.content.Context
import androidx.room.Room
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
        @ApplicationContext context: Context
    ): GalleryDB {
        return Room.databaseBuilder(
            context.applicationContext,
            GalleryDB::class.java,
            "gallery.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGalleryDao(
        galleryDB: GalleryDB
    ): GalleryDao {
        return galleryDB.galleryDao()
    }
}