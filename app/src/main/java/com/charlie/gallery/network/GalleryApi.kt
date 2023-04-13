package com.charlie.gallery.network

import com.charlie.gallery.model.ImageDataDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GalleryApi {

    @GET("/v2/list")
    fun requestImageList(
        @Query("page")
        page: Int = 0,
        @Query("limit")
        limit: Int = 30,
    ): Call<List<ImageDataDto>>

    @GET
    fun requestImage(
        @Url url: String
    ): Call<ImageDataDto>
}