package com.charlie.gallery.network

import okhttp3.logging.HttpLoggingInterceptor

object Interceptor {
    val httpLoginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }
}