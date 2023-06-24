package com.charlie.remote.di

import com.charlie.remote.api.ImageApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://picsum.photos"

    @Provides
    @Singleton
    fun provideLimit(): Int = 30

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(
        kotlinJsonAdapterFactory: KotlinJsonAdapterFactory,
    ): Moshi = Moshi
        .Builder()
        .add(kotlinJsonAdapterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(
        moshi: Moshi,
    ): MoshiConverterFactory = MoshiConverterFactory.create(moshi)


    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideImageApi(
        retrofit: Retrofit,
    ): ImageApi = retrofit.create(ImageApi::class.java)
}