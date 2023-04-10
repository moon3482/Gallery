package com.charlie.gallery.model

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "download_url")
    val downUrl: String,
)
