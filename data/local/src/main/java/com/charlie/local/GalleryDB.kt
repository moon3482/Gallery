package com.charlie.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.charlie.local.model.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class GalleryDB : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}
