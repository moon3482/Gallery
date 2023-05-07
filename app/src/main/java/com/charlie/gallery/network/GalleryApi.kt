package com.charlie.gallery.network

import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryApi {

    @GET("/v2/list")
    fun requestImageList(
        @Query("page")
        page: Int = 0,
        @Query("limit")
        limit: Int = 30,
    ): Call<List<ImageItemData>>

    @GET("/id/{id}/info")
    fun requestImage(
        @Path("id") id: Int,
    ): Call<ImageDetailData>
}