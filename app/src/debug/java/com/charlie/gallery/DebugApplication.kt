package com.charlie.gallery

import timber.log.Timber

class DebugApplication : GalleryApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}