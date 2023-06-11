package com.charlie.gallery.db

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object GalleryDatabase {
    @Volatile
    private var database: GalleryDB? = null

    @OptIn(InternalCoroutinesApi::class)
    fun getDatabase(context: Context): GalleryDB {
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
        return database!!
    }
}
