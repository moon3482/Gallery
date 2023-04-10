package com.charlie.gallery.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://picsum.photos"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private fun getRetrofitClient(): Retrofit {
        if (retrofit == null)
            @Volatile
            retrofit = Retrofit.Builder()
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
        return retrofit!!
    }

    val galleryApi: GalleryApi by lazy { getRetrofitClient().create(GalleryApi::class.java) }
}