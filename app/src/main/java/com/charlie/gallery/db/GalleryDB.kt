package com.charlie.gallery.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.charlie.gallery.local.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class GalleryDB : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}
