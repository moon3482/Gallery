package com.charlie.gallery.network

import com.charlie.gallery.model.ImageData
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
    ): Call<List<ImageData>>
}