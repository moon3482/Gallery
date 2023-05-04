package com.charlie.gallery.network

import com.charlie.gallery.model.ImageData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryApi {

    @GET("/v2/list")
    suspend fun requestImageList(
        @Query("page")
        page: Int = 0,
        @Query("limit")
        limit: Int = 30,
    ): List<ImageData>

    @GET("/id/{id}/info")
    suspend fun requestImage(
        @Path("id") id: Int,
    ): ImageData
}