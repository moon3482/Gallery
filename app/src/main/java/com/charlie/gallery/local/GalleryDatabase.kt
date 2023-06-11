package com.charlie.gallery.local

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object GalleryDatabase {
    @Volatile
    private var database: GalleryDB? = null

    @OptIn(InternalCoroutinesApi::class)
    fun init(context: Context) {
        if (database == null) {
            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GalleryDB::class.java,
                    "gallery.db"
                ).build().also {
                    database = it
                }
            }
        }
    }

    val galleryDao: GalleryDao
        get() = database?.galleryDao()
            ?: throw IllegalStateException("GalleryDatabase must be initialized")

}
