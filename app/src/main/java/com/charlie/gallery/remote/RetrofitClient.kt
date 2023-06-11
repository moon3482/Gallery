package com.charlie.gallery.remote

import com.charlie.gallery.network.Interceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object RetrofitClient {

    private const val BASE_URL: String = "https://picsum.photos"
    private val kotlinJsonAdapterFactory: KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()
    private val moshi: Moshi = Moshi
        .Builder()
        .add(kotlinJsonAdapterFactory)
        .build()
    private val moshiConverterFactory: MoshiConverterFactory = MoshiConverterFactory.create(moshi)
    private val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(Interceptor.httpLoginInterceptor)
        .build()
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val galleryApi: GalleryApi = retrofit.create()
}
