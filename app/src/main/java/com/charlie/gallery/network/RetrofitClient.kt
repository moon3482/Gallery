package com.charlie.gallery.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitClient {

    private val BASE_URL = "https://picsum.photos"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    val galleryApi: GalleryApi = retrofit.create()
}