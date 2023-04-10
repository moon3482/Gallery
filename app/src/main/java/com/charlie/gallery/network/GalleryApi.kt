package com.charlie.gallery.network

import com.charlie.gallery.model.ImageDataDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {

    @GET("/v2/list")
    fun getImageList(
        @Query("page")
        page: Int = 0,
        @Query("limit")
        limit: Int = 30,
    ): Call<List<ImageDataDto>>
}