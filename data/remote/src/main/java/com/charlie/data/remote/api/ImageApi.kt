package com.charlie.data.remote.api

import com.charlie.data.remote.model.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("/v2/list")
    fun requestImageList(
        @Query("page")
        page: Int = 0,
        @Query("limit")
        limit: Int,
    ): Call<List<ImageResponse>>

    @GET("/id/{id}/info")
    fun requestImage(
        @Path("id") id: Int,
    ): Call<ImageResponse>
}
