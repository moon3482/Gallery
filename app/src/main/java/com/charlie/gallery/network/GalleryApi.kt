package com.charlie.gallery.network

import com.charlie.gallery.model.ImageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryApi {

    @GET("/v2/list")
    fun requestImageList(
        @Query("page")
        page: Int,
        @Query("limit")
        limit: Int,
    ): Call<List<ImageData>>

    @GET("/id/{id}/info")
    fun requestImage(
        @Path("id") id: Int
    ): Call<ImageData>
}