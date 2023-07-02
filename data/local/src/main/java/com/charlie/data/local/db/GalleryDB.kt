package com.charlie.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.charlie.data.local.model.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class GalleryDB : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
