package com.charlie.gallery.remote

import com.charlie.gallery.remote.model.ImageDetailDataResponse
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
        limit: Int,
    ): Call<List<ImageDetailDataResponse>>

    @GET("/id/{id}/info")
    fun requestImageDetail(
        @Path("id") id: Int,
    ): Call<ImageDetailDataResponse>
}
