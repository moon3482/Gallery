package com.charlie.gallery

import android.app.Application
import com.charlie.gallery.local.GalleryDatabase

open class GalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GalleryDatabase.init(this)
    }
}
