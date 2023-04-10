package com.charlie.gallery.network

import com.charlie.gallery.model.ImageDto
import retrofit2.Call
import retrofit2.http.GET

interface GalleryApi {

    @GET("v2/list")
    fun getImageList(): Call<List<ImageDto>>
}