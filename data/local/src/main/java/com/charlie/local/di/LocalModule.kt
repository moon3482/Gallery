package com.charlie.local.di

import android.content.Context
import androidx.room.Room
import com.charlie.local.db.GalleryDB
import com.charlie.local.db.GalleryDao
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