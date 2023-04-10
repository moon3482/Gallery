package com.charlie.gallery.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://picsum.photos/"

    private fun getRetrofitClient(): Retrofit {
        if (retrofit == null)
            @Volatile
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        return retrofit!!
    }

    val galleryApi by lazy { getRetrofitClient().create(GalleryApi::class.java) }
}